Excel Parser
===

Excel Parser는 Spring boot 애플리케이션에서 간편하게 Excel 데이터를 추출하는 기능을 제공합니다.
Apache POI 라이브러리를 사용하여 API를 추상화하였으며, Excel Parser를 사용함으로써, 사용자는 비즈니스 로직 작성에 집중할 수 있습니다.

<br />

- DOM 방식 XSSFWorkBook과 SAX 방식 XMLReader를 모두 지원합니다.
- Spring Boot 기동시, Entity 메타정보 Validation 및 Circular Dependency 검사 기능을 제공합니다.
- 순차, 비순차, 부분범위 데이터 추출이 가능하며, JPA 스타일의 다양한 Annotation 지원으로 인해 클래스 재사용이 용이합니다.
- MessageConverter 기능을 통해서 Header명에 대한 다국어 처리가 가능합니다.
- 메타 데이터 Caching 기능을 제공하여 빠른 파싱이 가능합니다.

<br />

그 밖에 다양한 기능 및 사용법은 [Wiki](https://github.com/cla9/excel-parser-spring-boot-starter/wiki) 페이지를 참고 바랍니다.

<br />

Compatibility
---
Spring Boot 2.2 이상

<br />

Release
---

안정화 버전 : 1.2.1

Hot to use
---

빠르게 Excel Parser를 활용하는 방법에 대해서 간단히 소개합니다.
Gradle을 Build 도구로 사용한다면, 아래와 같이 라이브러리를 추가할 수 있습니다.


```groovy
dependencies {
    implementation 'com.github.cla9:excel-parser-spring-boot-starter:1.2.1'
}    
```

<br />

Maven을 사용한다면, 다음과 같이 사용 가능합니다.


```xml
<dependency>
  <groupId>com.github.cla9</groupId>
  <artifactId>excel-parser-spring-boot-starter</artifactId>
  <version>1.2.1</version>
</dependency>
```

<br />

Spring Boot 기동시, Entity Validation 기능을 해제하려면, 다음과 같이 application.yml 파일 옵션을 설정할 수 있습니다.    


```yaml
excel:
  entity:
    validation:
      enabled: true
```

<br />


Excel Parser를 사용하기 위해서 다음과 같이 Annotation 기반의 메타 데이터를 작성해야합니다. 이때 __@ExcelBody__ Annotation은 필수 입력 대상입니다.
> Entity에는 반드시 Default Constructor가 존재해야합니다.

<br />

Excel 파일 모든 Column 데이터 파싱 방법(@ExcelBody Annotation만 기술)
```java

@ExcelBody(dataRowPos = 2)
public class Character {
    private Integer no;
    private String level;
    private String description;
}
```

<br />

Excel 파일 일부 Column 데이터 파싱 방법(@ExcelColumn Annotation 추가 기술)

```java
@ExcelBody(dataRowPos = 2)
public class Character{
    @ExcelColumn(headerName = "번호")
    private Integer no;
    
    @ExcelColumn(headerName = "등급")
    private String level;
    private String description;
}
```

위 코드 Parsing 결과는 @ExcelColumn이 포함된 no,level 데이터만 추출됩니다.

<br />

Excel Parser에서 제공하는 모든 Annotation을 적용하면, 다음과 같은 모습의 Entitiy 클래스를 디자인할 수 있습니다.  


```java
@ExcelBody(dataRowPos = 3, 
           type = ReaderType.SAX,
           headerRowRange = @RowRange(start = 1, end = 2),
           messageSource = PersonMessageConverter.class)
@ExcelBody(dataRowPos = 2)
@ExcelMetaCachePut
@ExcelColumnOverrides({      
        @ExcelColumnOverride(headerName = "수정일", index = 10, column = @ExcelColumn(headerName = "수정일자"))
})
public class Person extends BaseAuditEntity{
    @ExcelColumn(headerName = "이름")
    private String name;
    
    @Merge(headerName = "전화번호")
    @ExcelColumnOverrides(@ExcelColumnOverride(headerName = "집전화번호", index = 5, column = @ExcelColumn(headerName = "휴대전화번호", index = 4)))
    private Phone phone;
    
    @ExcelEmbedded
    private Address address;
    
    @ExcelColumn(headerName = "생성일자")
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate createdAt;
    
    @ExcelColumn(headerName = "성별")
    @ExcelConvert(converter = GenderConverter.class)
    private Gender gender;
}
```

<br />

메타데이터를 작성하고 나면, 아래와 같은 방법으로 데이터를 추출할 수 있습니다. 이때 Streaming 방식과, Collection 방식으로 결과를 추출할 수 있습니다.

<br />

방법1. Collection 방식(Controller 파라미터로 주입받는 방법)
```java
@RestController
public class SampleController {
    @PostMapping(value = "/form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExcelResultSet<Person>> getExcelResult(@ExcelRequestBody ExcelResultSet<Person> resultSet) {
        //your code
        return ResponseEntity.ok(resultSet);
    }
}
```

<br />

방법2. Collection 방식(ExcelTemplate Bean으로 추출)

```java
@RestController
public class SampleController {
    private final ExcelTemplate excelTemplate;

    public SampleController(ExcelTemplate excelTemplate) {
        this.excelTemplate = excelTemplate;
    }

    @PostMapping(value = "/form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExcelResultSet<Person>> getExcelResult(@RequestParam("file") MultipartFile file) {
        final ExcelResultSet<Person> resultSet = excelTemplate.createResultSet(Person.class, file);
        //your code
        return ResponseEntity.ok(resultSet);
    }
}
```

<br />

방법3. Streaming 방식(ExcelTemplate Bean으로 추출)

```java
@RestController
public class SampleController {
    private final ExcelTemplate excelTemplate;

    public SampleController(ExcelTemplate excelTemplate) {
        this.excelTemplate = excelTemplate;
    }

    @PostMapping(value = "/form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> getExcelResult(@RequestParam("file") MultipartFile file) {
        excelTemplate.parse(Person.class, file, Person -> {
            //On Success Callback
        }, error -> {
            //On Failure Callback
        });
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
```

<br />

Supported Injection Types
---


<br />

Primitive(Wrapper) 타입이나, String 그리고 JSR310 관련 타입(LocalDate, LocalTime, LocalDateTime, ZonedDateTime, OffsetDateTime, OffsetTime)을 지원합니다.
날짜 관련 데이터 타입의 경우에는 __@DateTimeFormat__ Annotation이 반드시 필요합니다.


```java
public class BaseAuditEntity {
    @ExcelColumn(headerName = "생성자")
    private String creator;

    @ExcelColumn(headerName = "생성일자")
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate createdAt;
   
    @ExcelColumn(headerName = "수정자")
    private String updater;

    @ExcelColumn(headerName = "수정일자")
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate updatedAt;
}
```

<br />


License
---
Excel Parser is licensed under the Apache License, Version 2.0. See [LICENSE](https://github.com/cla9/excel-parser-spring-boot-starter/blob/master/LICENSE) for full license text.
