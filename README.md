## 목차

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
	    - 개발내용
        </td>
        <td>
	    - 개발내용
        </td>
        <td>
	    - 개발내용
        </td>
    </tr>
</table>

## 서비스 구성 및 실행방법

</br>

## 프로젝트 목적/상세

</br>


## ERD
![image](https://github.com/user-attachments/assets/49e24c58-b6c3-483c-b727-b17c92e96106)

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

## API DOCs (추가예정)

<details> <summary> <b> Users </b> </summary> 

| API 엔드포인트          | HTTP 메서드 | 설명             | request  | response
|--------------------|----------|----------------|-----------------------------|-----------------------------|
| /api/users/sign-up | POST     | 회원가입  | ![image](https://github.com/user-attachments/assets/edc88ec0-ed68-427d-a1d7-ce97ee239f92) | ![image](https://github.com/user-attachments/assets/f4642881-9c4d-44b9-8e30-97f639c98960)
| /api/users/sign-in | POST     | 로그인 |

</details>

<details> <summary> <b> Stores </b> </summary> </details>
  
<details> <summary> <b> Orders </b> </summary> </details>
  
<details> <summary> <b> Payments </b> </summary> </details>
  
<details> <summary> <b> Products </b> </summary> </details>
  
<details> <summary> <b> Carts </b> </summary> </details>


</details>

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




