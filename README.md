## 목차
0. 프로젝트 소개
1. [역할분담](#역할분담)
2. [서비스 구성 및 실행방법](#서비스-구성-및-실행방법)
3. [프로젝트 목적/상세](#프로젝트-목적상세)
5. [기술 스택](#기술-스택)
6. [API DOCS](#api-docs)
7. [파일구조](#파일구조)

</br>
</br>

   
# 저기요 🛵

### 인공지능 챗봇을 적용한 배달 주문 서비스

#### 프로젝트 기간: 24년 8월 26일 ~ 9월 2일 (8일)

#### 역할분담
<table>
    <tr>
        <th>창다은</th>
        <th>김진명</th>
        <th>김정용</th>
    </tr>
    <tr>
	<td>
	    - Order 서비스 개발 </br>
	    - Chat 서비스 개발
        </td>
        <td>
	    - User, Auth 서비스 개발</br>
	    - Cart 서비스 개발</br>
	    - 서비스 배포
        </td>
        <td>
	    - Store 서비스 개발</br>
	    - Product 서비스 개발
        </td>
    </tr>
</table>

</br>
</br>


## 서비스 구성 및 실행방법

### 프로젝트 아키텍처
![image](https://github.com/user-attachments/assets/afcb2fd2-b3b4-43e9-ba6e-c4e8395cf1e3)

</br>


### ERD
![image](https://github.com/user-attachments/assets/49e24c58-b6c3-483c-b727-b17c92e96106)

</br>

### 실행방법

DNS 주소 : http://ec2-13-125-69-125.ap-northeast-2.compute.amazonaws.com

***주소 뒤에 + :8080/{API} 를 붙여 request 요청***

ex) http://ec2-13-125-69-125.ap-northeast-2.compute.amazonaws.com:8080/api/users/sign-in

API는 목차 6번 [API DOCS](#api-docs) 로 확인할 수 있습니다.

</br>

## 프로젝트 목적/상세

</br>



</br>


## 기술 스택

#### BE
<div>
    <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
    <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge&logo=amazondocumentdb&logoColor=white">
    <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=spring security&logoColor=white">
</div>

#### DB
<div>
    <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=PostgreSQL&logoColor=white">
</div>

#### Collabo Tools
<div>
    <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
    <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
    <img src="https://img.shields.io/badge/notion-white?style=for-the-badge&logo=notion&logoColor=000000">
    <img src="https://img.shields.io/badge/erdcloud-000000?style=for-the-badge&logo=erdcloud&logoColor=white">
    <img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white">
</div>

</br>

## API DOCS

#### 아래 토글을 눌러 API를 확인할 수 있습니다. (request, response가 짤릴 경우 좌우 스크롤을 이용하여 확인 할 수 있습니다.) 😊

<!-----------------------------user api 시작--------------------------------------------------------------------------------------------------------------------------->
<details> <summary> <b> Users </b> </summary> 

<table>
  <tr>
    <th>API 엔드포인트</th>
    <th>HTTP 메서드</th>
    <th>설명</th>
    <th>request</th>
    <th>response</th>
  </tr>
  <tr>
    <td>/api/users/sign-up</td>
    <td>POST</td>
    <td>회원가입</td>
    <td>
      <pre>
{
  "username": "test1",
  "nickname": "저기용",
  "email": "test1@test.com",
  "password": "Asdf1234!",
  "address": "string",
  "roles": [
    "MASTER"
  ]
}
      </pre>
    </td>
    <td>
      <pre>
{
  "userId": "string",
  "username": "test1",
  "nickname": "저기용",
  "email": "test1@test.com",
  "address": "string",
  "roles": [
    "MASTER"
  ]
}
      </pre>
    </td>
  </tr>
  <tr>
    <td>/api/users/sign-in</td>
    <td>POST</td>
    <td>로그인</td>
    <td>
      <pre>
{
  "username": "test1",
  "password": "Asdf1234!"
}
      </pre>
    </td>
    <td>
      <pre>
{
    "message": "로그인에 성공하였습니다.",
    "data": {
        "username": "test1",
        "nickname": "저기용",
        "email": "test1@test.com",
        "address": "string",
        "roles": [
            "MASTER"
        ]
    }
}
</pre>
    </td>
  </tr>
  <tr>
    <td>/api/users/{userId}</td>
    <td>PUT</td>
    <td>유저 정보 수정</td>
    <td>
     <pre>
{
  "nickname": "거기용",
  "currentPassword": "Asdf1234!",
  "updatePassword": "NewP@ssw0rd123",
  "address": "서울특별시 강남구 역삼동 123-45"
}
</pre>
    </td>
    <td>
      <pre>
{
  "message": "수정되었습니다.",
  "data": {
    "username": "test1",
    "nickname": "거기용",
    "email": "test1@test.com",
    "address": "서울특별시 강남구 역삼동 123-45",
    "roles": [
      "CUSTOMER"
    ]
  }
}
      <pre>
    </td>
  </tr>
  <tr>
    <td>/api/users/{userId}</td>
    <td>GET</td>
    <td>유저 상세 조회</td>
    <td>
      <pre>
-
      </pre>
    </td>
    <td>
      <pre>
{
  "username": "test1",
  "nickname": "저기용",
  "email": "test1@test.com",
  "address": "string",
  "roles": [
    "CUSTOMER"
  ]
}
      </pre>
    </td>
  </tr>
  <tr>
    <td>/api/users?page={value}&size={value}&sortBy={value}&isAsc={boolean}{</td>
    <td>GET</td>
    <td>유저 전체 조회</td>
    <td>
      <pre>
-
      </pre>
    </td>
    <td>
      <pre>
{
  "message": "회원 전체 목록",
  "data": [
    {
      "content": [
        {
          "createdAt": "2024-08-27T12:33:28.063044",
          "createdBy": null,
          "updatedAt": "2024-08-27T12:33:28.063044",
          "updatedBy": null
        }
      ]
    }
  ]
}
      </pre>
    </td>
  </tr>
  <tr>
    <td>/api/users/{userId}</td>
    <td>DELETE</td>
    <td>회원 탈퇴</td>
    <td>
      <pre>
{
  "password": "Asdf1234!"
}
      </pre>
    </td>
    <td>
      <pre>
204 No content
      </pre>
    </td>
  </tr>

</table>
</details>

<!-----------------------------user api 끝--------------------------------------------------------------------------------------------------------------------------->
<!-----------------------------store api 시작--------------------------------------------------------------------------------------------------------------------------->
<details> <summary> <b> Store </b> </summary> 

<table>
  <tr>
    <th>API 엔드포인트</th>
    <th>HTTP 메서드</th>
    <th>설명</th>
    <th>request</th>
    <th>response</th>
  </tr>
  <tr>
    <td>/api/stores</td>
    <td>POST</td>
    <td>가게 등록</td>
    <td>
      <pre>
{
  "storeName": "My New Store",
  "storeNumber": "123-456-7890",
  "category": "CHINESE_FOOD"
}
      </pre>
    </td>
    <td>
      <pre>
{
  "message": "가게 생성을 성공하였습니다.",
  "data": {
    "storeId": "3c135244-b859-4878-926d-6732ea7abd84",
    "storeName": "My New Store",
    "storeNumber": "123-456-7890",
    "category": "CHINESE_FOOD"
  }
}
      </pre>
    </td>
  </tr>
  <tr>
    <td>/api/stores/{storeId}</td>
    <td>GET</td>
    <td>가게 상세 조회</td>
    <td>
     <pre>
-
</pre>
    </td>
    <td>
      <pre>
{
  "message": "단건 조회 성공하였습니다.",
  "data": {
    "storeId": "3c135244-b859-4878-926d-6732ea7abd84",
    "storeName": "My New Store",
    "storeNumber": "123-456-7890",
    "category": "CHINESE_FOOD"
  }
}
      <pre>
    </td>
  </tr>
  <tr>
    <td>/api/stores?page={value}&size={value}&sortBy={value}&isAsc={boolean}</td>
    <td>GET</td>
    <td>가게 전체 조회</td>
    <td>
      <pre>
-
      </pre>
    </td>
    <td>
      <pre>
{
  "message": "가게 조회를 성공하였습니다.",
  "data": {
    "content": [
      {
        "storeId": "3c135244-b859-4878-926d-6732ea7abd84",
        "storeName": "My Old Store",
        "storeNumber": "321-654-0987",
        "category": "KOREAN_FOOD"
      },
      {
        "storeId": "1b635244-b859-4878-926d-6732ea7abd85",
        "storeName": "Another Store",
        "storeNumber": "987-654-3210",
        "category": "JAPANESE_FOOD"
      }
    ]
  }
}
</pre>
    </td>
  </tr>
  <tr>
    <td>/api/stores/{storeId}</td>
    <td>PUT</td>
    <td>가게 수정</td>
    <td>
      <pre>
{
  "storeName": "My Old Store",
  "storeNumber": "321-654-0987",
  "category": "KOREAN_FOOD"
}
      </pre>
    </td>
    <td>
      <pre>
{
  "message": "가게 내용 수정에 성공하였습니다.",
  "data": {
    "storeId": "3c135244-b859-4878-926d-6732ea7abd84",
    "storeName": "My Old Store",
    "storeNumber": "321-654-0987",
    "category": "KOREAN_FOOD"
  }
}
      </pre>
    </td>
  </tr>
  <tr>
    <td>/api/stores/{storeId}</td>
    <td>DELETE</td>
    <td>가게 삭제</td>
    <td>
      <pre>
-
      </pre>
    </td>
    <td>
      <pre>
204 No content
      </pre>
    </td>
  </tr>

</table>
</details>

<!-----------------------------store api 끝--------------------------------------------------------------------------------------------------------------------------->
<!-----------------------------order api 시작--------------------------------------------------------------------------------------------------------------------------->
<details> <summary> <b> Order </b> </summary>

<table>
  <tr>
    <th>API 엔드포인트</th>
    <th>HTTP 메서드</th>
    <th>설명</th>
    <th>request</th>
    <th>response</th>
  </tr>
  <tr>
    <td>/api/orders</td>
    <td>POST</td>
    <td>주문 등록</td>
    <td>
      <pre>
{
  "requirement": "배달 빨리해주세요"
}
      </pre>
    </td>
    <td>
      <pre>
{
  "message": "주문 성공",
  "data": null
}
      </pre>
    </td>
  </tr>
  <tr>
    <td>/api/orders?page=?&size=?&sort=?</td>
    <td>GET</td>
    <td>주문 전체 조회</td>
    <td>
      <pre>
-
      </pre>
    </td>
    <td>
      <pre>
{
  "message": "주문 전체 조회 성공",
  "data": [
    {
      "orderId": "a864984a-ee05-4795-af17-fdfec3e157d4",
      "userId": 52,
      "storeId": "0cb914e0-213f-4eb4-aafc-11c5adc51fd6",
      "requirement": "배달 빨리해주세요",
      "isDeleted": false,
      "productOrderList": null
    }
  ]
}
</pre>
    </td>
  </tr>
  <tr>
    <td>/api/orders/{orderId}</td>
    <td>GET</td>
    <td>주문 상세 조회</td>
    <td>
     <pre>
-
</pre>
    </td>
    <td>
      <pre>
{
  "message": "주문 상세 조회 성공",
  "data": {
    "orderId": "a864984a-ee05-4795-af17-fdfec3e157d4",
    "userId": 52,
    "storeId": "0cb914e0-213f-4eb4-aafc-11c5adc51fd6",
    "requirement": "배달 빨리해주세요",
    "isDeleted": false,
    "productOrderList": [
      {
        "productOrderId": 1,
        "productId": "3c953ec1-2abe-4e6d-ab17-9bccf269d87f",
        "quantity": 2,
        "productName": "만두"
      }
    ]
  }
}
      <pre>
    </td>
  </tr>
  <tr>
    <td>/api/orders/{orderId}</td>
    <td>DELETE</td>
    <td>주문 삭제</td>
    <td>
      <pre>
-
      </pre>
    </td>
    <td>
      <pre>
{
"message": "주문 삭제 성공",
"data": null
}
      </pre>
    </td>
  </tr>
  <tr>
    <td>/api/orders/search?orderNumber={orderId}</td>
    <td>GET</td>
    <td>주문 내역 검색</td>
    <td>
      <pre>
-
      </pre>
    </td>
    <td>
      <pre>
{
"message": "주문 검색 성공",
"data": {
"orderId": "a864984a-ee05-4795-af17-fdfec3e157d4",
"userId": 52,
"storeId": "0cb914e0-213f-4eb4-aafc-11c5adc51fd6",
"requirement": "배달 빨리해주세요",
"isDeleted": false,
"productOrderList": null
}
}
      </pre>
    </td>
  </tr>

</table>
 
</details>
<!-----------------------------order api 끝--------------------------------------------------------------------------------------------------------------------------->
<!-----------------------------product api 시작--------------------------------------------------------------------------------------------------------------------------->
<details> <summary> <b> Product </b> </summary> 

<table>
  <tr>
    <th>API 엔드포인트</th>
    <th>HTTP 메서드</th>
    <th>설명</th>
    <th>request</th>
    <th>response</th>
  </tr>
  <tr>
    <td>/api/products</td>
    <td>POST</td>
    <td>상품 생성</td>
    <td>
      <pre>
{
  "productName": "상품 A",
  "productPrice": 2000,
  "productExplain": "상품 A 설명"
}
      </pre>
    </td>
    <td>
      <pre>
{
  "message": "상품 생성을 성공하였습니다.",
  "data": {
    "productName": "상품 A",
    "productPrice": 2000,
    "productExplain": "상품 A 설명"
  }
}
      </pre>
    </td>
  </tr>
  <tr>
    <td>/api/products?page={value}&size={value}&sortBy={value}&isAsc={boolean}</td>
    <td>GET</td>
    <td>상품 전체 조회</td>
    <td>
      <pre>
-
      </pre>
    </td>
    <td>
      <pre>
{
  "message": "상품 조회를 성공하였습니다.",
  "data": {
    "content": [
      {
        "productId": "d5a1e7b8-0e8f-4f71-9d5c-2c68a9d5b2a4",
        "productName": "상품 A",
        "productPrice": 1000,
        "productExplain": "상품 A 설명"
      },
      {
        "productId": "e8a1b9c4-1a2b-4f60-9f91-4f5b9d5b6c4d",
        "productName": "상품 B",
        "productPrice": 2000,
        "productExplain": "상품 B 설명"
      }
    ]
  }
}
</pre>
    </td>
  </tr>
  <tr>
    <td>/api/products/{productId}</td>
    <td>GET</td>
    <td>상품 상세 조회</td>
    <td>
     <pre>
-
</pre>
    </td>
    <td>
      <pre>
{
  "message": "단건 조회 성공하였습니다.",
  "data": {
    "productId": "e8a1b9c4-1a2b-4f60-9f91-4f5b9d5b6c4d",
    "productName": "상품 A",
    "productPrice": 2000,
    "productExplain": "상품 A 설명"
  }
}
      <pre>
    </td>
  </tr>
  <tr>
    <td>/api/products/{productId}</td>
    <td>PUT</td>
    <td>상품 정보 수정</td>
    <td>
      <pre>
{
  "productName": "상품 B",
  "productPrice": 4000,
  "productExplain": "상품 B 설명"
}
      </pre>
    </td>
    <td>
      <pre>
{
  "message": "가게 내용 수정에 성공하였습니다.",
  "data": {
    "productName": "상품 B",
    "productPrice": 4000,
    "productExplain": "상품 B 설명"
  }
}
      </pre>
    </td>
  </tr>
  <tr>
    <td>/api/products/{productId}</td>
    <td>DELETE</td>
    <td>상품 삭제</td>
    <td>
      <pre>
-
      </pre>
    </td>
    <td>
      <pre>
204 No content
      </pre>
    </td>
  </tr>

</table>
</details>

<!-----------------------------product api 끝--------------------------------------------------------------------------------------------------------------------------->
<!-----------------------------carts api 시작--------------------------------------------------------------------------------------------------------------------------->
<details> <summary> <b> Carts </b> </summary> 

<table>
  <tr>
    <th>API 엔드포인트</th>
    <th>HTTP 메서드</th>
    <th>설명</th>
    <th>request</th>
    <th>response</th>
  </tr>
  <tr>
    <td>/api/carts/products</td>
    <td>POST</td>
    <td>장바구니 메뉴 담기</td>
    <td>
      <pre>
{
  "storeId": "45a62637-6fe9-4201-826a-9062b30e8d52",
  "productId": "45a62637-6fe9-4201-826a-9062b30e8d50",
  "quantity": 2
}
      </pre>
    </td>
    <td>
      <pre>
{
  "message": "장바구니에 성공적으로 담겼습니다.",
  "data": {
    "userId": 1,
    "products": [
      {
        "cartId": "8dc610ee-30ca-4a02-aaf4-37bcdf1353ba",
        "storeId": "45a62637-6fe9-4201-826a-9062b30e8d52",
        "storeName": "My New Store1",
        "productId": "45a62637-6fe9-4201-826a-9062b30e8d51",
        "productName": "test",
        "productPrice": 100,
        "quantity": 4
      },
      {
        "cartId": "8097058c-ace3-49a4-8c20-df42ee1526c7",
        "storeId": "45a62637-6fe9-4201-826a-9062b30e8d52",
        "storeName": "My New Store1",
        "productId": "45a62637-6fe9-4201-826a-9062b30e8d50",
        "productName": "test2",
        "productPrice": 500,
        "quantity": 2
      }
    ],
    "cartTotalPrice": 1400,
    "totalQuantity": 6
  }
}
      </pre>
    </td>
  </tr>
  <tr>
    <td>/api/carts/products</td>
    <td>PUT</td>
    <td>장바구니 메뉴 수량 수정</td>
    <td>
      <pre>
{
  "productId": "45a62637-6fe9-4201-826a-9062b30e8d51",
  "quantity": 4
}
      </pre>
    </td>
    <td>
      <pre>
{
  "message": "수정되었습니다.",
  "data": {
    "userId": 1,
    "products": [
      {
        "cartId": "8dc610ee-30ca-4a02-aaf4-37bcdf1353ba",
        "storeId": "45a62637-6fe9-4201-826a-9062b30e8d52",
        "storeName": "My New Store1",
        "productId": "45a62637-6fe9-4201-826a-9062b30e8d51",
        "productName": "test",
        "productPrice": 100,
        "quantity": 4
      }
    ],
    "cartTotalPrice": 200,
    "totalQuantity": 4
  }
}
</pre>
    </td>
  </tr>
  <tr>
    <td>/api/carts/products/{productId}</td>
    <td>DELETE</td>
    <td>장바구니 메뉴 삭제</td>
    <td>
     <pre>
-
</pre>
    </td>
    <td>
      <pre>
204 No content
      <pre>
    </td>
  </tr>
  <tr>
    <td>/api/carts/products</td>
    <td>DELETE</td>
    <td>장바구니 전체 삭제</td>
    <td>
      <pre>
-
      </pre>
    </td>
    <td>
      <pre>
204 No content
      </pre>
    </td>
  </tr>
  <tr>
    <td>/api/carts</td>
    <td>GET</td>
    <td>장바구니 조회</td>
    <td>
      <pre>
-
      </pre>
    </td>
    <td>
      <pre>
{
  "userId": 1,
  "products": [
    {
      "cartId": "8dc610ee-30ca-4a02-aaf4-37bcdf1353ba",
      "storeId": "45a62637-6fe9-4201-826a-9062b30e8d52",
      "storeName": "My New Store1",
      "productId": "45a62637-6fe9-4201-826a-9062b30e8d51",
      "productName": "test",
      "productPrice": 100,
      "quantity": 4
    }
  ],
  "cartTotalPrice": 200,
  "totalQuantity": 4
}
      </pre>
    </td>
  </tr>

</table>
</details>

<!-----------------------------carts api 끝--------------------------------------------------------------------------------------------------------------------------->
<!-----------------------------chats api 시작--------------------------------------------------------------------------------------------------------------------------->
<details> <summary> <b> Chats </b> </summary> 

<table>
  <tr>
    <th>API 엔드포인트</th>
    <th>HTTP 메서드</th>
    <th>설명</th>
    <th>request</th>
    <th>response</th>
  </tr>
  <tr>
    <td>/api/chats</td>
    <td>POST</td>
    <td>챗봇 질문/답변</td>
    <td>
      <pre>
{
  "contents": {
    "parts": {
      "text": "짜장면 이름 추천해줘"
    }
  }
}
      </pre>
    </td>
    <td>
      <pre>
{
    "message": "채팅 성공",
    "data": {
        "answer": "## 짜장면 이름 추천 (100자 안)\n\n* ...
    }
}
      </pre>
    </td>
  </tr>
</table>
</details>

<!-----------------------------chats api 끝--------------------------------------------------------------------------------------------------------------------------->

</br>

## 파일구조
```
com
  ㄴ sparta
     ㄴ jeogiyo
        ㄴ domain
        |   ㄴ order
        |   | ㄴ controller
        |   | ㄴ dto
        |   |   ㄴ response
        |   |   ㄴ request
        |   | ㄴ entity
        |   | ㄴ repository
        |   | ㄴ service
        |   ㄴ user
        |   | ㄴ controller
        |   | ㄴ dto
        |   |   ㄴ response
        |   |   ㄴ request
        |   | ㄴ entity
        |   | ㄴ repository
        |   | ㄴ service
        | ...
        ㄴ global
        |   ㄴ config
        |   ㄴ detail
        |   ㄴ entity
        |   ㄴ jwt
        |   ㄴ response
        |
        ㄴ   JeogiyoApplication

```




