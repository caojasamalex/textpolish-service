# Text Polish Service API

Text Polish Service PI serves as a mediator between your comapny and external Proofreader Service API. This API handles the following:

- Request Validation and Processing: Validating if the provided language and domain are supported by the external API. Before forwarding requests, the API removes HTML and other type of tags using it's utility methods.

- Forwards valid requests to the external service and receives processed text.

- Calculates similarity: This service calculates similarity between the original and proofread content using Apache Commons Text algorithm.

- Caching: Uses Spring Cache to cache supported languages and domains to ensure less calls and lower the overal cost for using the external API.
## Endpoints

```http
  POST /polish
```

This endpoint receives text processing requests. Before forwarding the request body to the external API, validation is conducted and HTML tags are removed. After the external API returns processed text, API calculates similarity and provides that in a return body.

**JSON Request - Explanation**

| Parameter | Type     | Description                                  |
| :-------- | :------- | :------------------------------------------- |
| `language`| `string` | **Required** - Language code (e.g. "en-EN")  |
| `domain`  | `string` | **Required** - Domain type (e.g. "business") |
| `content` | `string` | **Required** - Text to be processed          |

**JSON Response - Example**

```json
{
  "language": "en-EN",
  "domain": "business",
  "content": "<mark>Test</mark> this is an example text"
}
```

**JSON Response - Explanation**

| Parameter         | Type     | Description                                 |
| :---------------- | :------- | :------------------------------------------ |
| `polished_content`| `string` | Text processed by the external API                    |
| `similarity`      | `number` | Calculated similarity between the original and proofed content        |

**JSON Response - Example**
```json
{
  "polished_content": "Test this is an example text",
  "similarity": 0.85
}
```
## Request Handling

* **Tags removal** - Method for removal of tags is called to ensure HTML tags are not present in the text.

* **Word count validation** - Controller checks count of words and, if the count doesn't meet the requirements, error is thrown.

* **Field validation** - Basic validation (@NotBlank and others) is performed before processing the request.

* **Request Processing**:
   - It checks if the language and domain are supported (using cached data).
   - The request is forwarded to the external Proofreading Service.
   - The similarity score between the original and the polished content is calculated.
   - The final response is returned.
## Using Postman
To test the API using Postman:
- Select Method: POST
- URL: http://localhost:8080/polish
- Headers:
  >`Content-Type: application/json`
- Body: Choose *raw* and format as *JSON*. Enter the following example:
  ```json
  {
    "language": "en-EN",
    "domain": "business",
    "content": "<mark>Test</mark> this is an example text"
  }
  ```
- Send Request:

> If all conditions are met, you will receive a response with the processed text and the similarity score.
> If a condition is not met (e.g. the word count exceeds 30), you will get **HTTP Bad Request** response.

## Running the Application
**Download and Build the Project:**

* Use Maven command: `mvn clean install`

**Run the Application:**
* Use the following command: `mvn spring-boot:run`

**Test the API:**
* Once the application is running, you can test the API using tools like Postman or curl.
