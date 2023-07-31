# 🏥 BodyTalk

<div align="center">
   
### 프로젝트 소개
사용자로부터 입력받은 증상을 바탕으로 예상되는 질병을 알려주는 서비스

### 프로젝트 목적
코로나 19와 같은 전염병으로 병원에 가서 진단받기 곤란할 때, 몸이 아프고 컨디션이 안 좋아서 병원에 가서 진료를 받기 쉽지않을 때<br>
병을 방치할 경우 처음 증상을 인지했을 때 보다 몸 상태가 악화되기 때문에 **자가진단 할 수 있는 서비스의 필요성을 느끼게 되어 개발**

### 개발기간
2023.02.06 - 2023.07.05

### 멤버구성
정지원 - Back-end, Front-end, AI
<br>
이성준 - Front-end

</div>

# 📚 기술스택

<div align="center">
   
### Languages
<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <img  src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/python-3776AB?style=for-the-badge&logo=python&logoColor=white"> 

### Technologies
<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black"> <img alt="Spring Boot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/django-092E20?style=for-the-badge&logo=django&logoColor=white"> <img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white">

### Collaboration Tool
<img alt="Git" src ="https://img.shields.io/badge/Git-F05032.svg?&style=for-the-badge&logo=Git&logoColor=white"/> <img alt="Notion" src ="https://img.shields.io/badge/Notion-000000.svg?&style=for-the-badge&logo=Notion&logoColor=white"/>

</div>

# 📌 주요기능

<details>
   <summary> 펼쳐보기👈</summary>
   
## Web
### 회원가입 및 로그인

<div align="center">
   
   <img src="/img/register.png" alt="register" style="width: 30%;">
   
   회원가입을 하게 되면 기본정보를 입력받게 됩니다. <br>
   성별에 따라서 증상을 진단받을 때, 다른결과가 나옵니다.
   
   <br>
   
   <img src="/img/login.png" alt="login" style="width: 30%;">
   
   구글, 카카오, 네이버 로그인을 제공합니다. <br>
   로그인을 하게 되면 최근 진단결과, 증상 일기등의 기능을 사용할 수 있습니다.

</div>

### 메인 화면 (진단 결과  순위)

<div align="center">
 <img src="/img/main.png" alt="main">
</div>

사용자들이 많이 진단받는 진단명들의 순위를 보여줍니다.

### 증상 진단

<div align="center">
 <img src="./img/diagnosis.gif" alt="diagnosis">
</div>

증상을 입력하면 Kobert 모델이 분석 후 유사도가 가장 높은 질병과 질병의 간략한 정보, 관련 병원을 추천합니다.

### 병원 찾기

 <div align="center">
 <img src="./img/hospital1.gif" alt="hospital1">
 #내 주변 병원찾기gif
</div>

진료 과목을 선택한 뒤 내 주변 병원찾기 버튼을 클릭하면 내 위치를 기반으로 가까운 병원을 찾아줍니다.

 <div align="center">
 <img src="./img/hospital2.gif" alt="hospital2">
 #지역명 병원찾기gif
</div>

진료 과목을 선택한 뒤 지역명으로 병원찾기 버튼을 클릭하면 주소를 입력받는 창이 나타납니다.
 주소를 입력하면 주소와  가까운 병원을 찾아줍니다.

 <div align="center">
 <img src="./img/hospital3.gif" alt="hospital3">
 #병원 클릭gif
</div>

나타난 병원들을 선택하게 되면 지도의 핀 색이 바뀝니다.

### 증상 일기

 <div align="center">
 <img src="./img/diarylogin.gif" alt="diarylogin">
</div>

증상 일기는 로그인이 필요한 서비스 입니다.

 <div align="center">
 <img src="./img/cal.gif" alt="cal">
</div>

달력에서 날짜를 선택해 증상 일기를 작성할 수 있습니다.

 <div align="center">
 <img src="./img/write.gif" alt="write">
</div>

일기는 증상에 맞는 태그를 5개까지 선택해서 작성할 수 있습니다.

 <div align="center">
 <img src="./img/check.gif" alt="check">
</div>

작성된 일기는 다시 볼 수 있고 수정할 수 있습니다.

 <div align="center">
 <img src="./img/check.gif" alt="check">
</div>

증상 진단과 작성한 일기의 태그를 통해 만들어진 증상 통계와 부위 별 통계를 확인할 수 있습니다.

### 마이페이지

 <div align="center">
 <img src="./img/mypage.png" alt="mypage">
</div>

내정보를 확인하고 수정할 수 있습니다.
최근 진단받은 진료기록을 한 눈에 볼 수 있습니다.

</details>
