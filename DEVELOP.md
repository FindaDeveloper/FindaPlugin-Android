# Develop

## 구조
핀다 플러그인 프로젝트는 MVP(Model-View-Presenter) 구조를 사용하여  
사용자의 상호작용과 로직을 분리합니다.  
아래는 전통적인 MVP 구조를 다이어그램으로 표현한 것입니다.
![](https://user-images.githubusercontent.com/36754680/108016880-e2d46200-7056-11eb-89e5-0cde21276060.png)

### View
사용자의 이벤트가 발생하는 부분입니다.  
프로젝트에서 대표적으로 [Action](https://plugins.jetbrains.com/docs/intellij/plugin-actions.html)에 해당합니다.  
동작에 필요한 정보(디렉토리, 입력 값)를 포함하고 있습니다.  
발생한 이벤트에 대한 로직을 수행하지 않고, Presenter로 정보와 이벤트를 전달합니다.

### Presenter
실질적으로 로직이 수행되는 부분입니다. View로부터 로직에 필요한 정보를 전달받습니다.  
정보와 이벤트를 기반으로 Model 계층으로 데이터 변경을 요청합니다.  
Model의 결과에 따라 적절하게 View를 업데이트합니다.

### Model
프로그램의 데이터를 직접적으로 조작하는 역할을 수행합니다.  
대표적으로 File 시스템과 관련된 작업을 수행하는 [FileHelper](https://github.com/FindaDeveloper/FindaTemplatePlugin-Android/blob/main/src/main/kotlin/kr/co/finda/androidtemplate/common/FileHelper.kt)가 있습니다.  

<br>

## 세부 다이어그램  
아래는 CreateFindaTemplate 기능에 대한 세부적인 다이어그램입니다.  
![](https://user-images.githubusercontent.com/36754680/108029084-38693880-7070-11eb-9a67-b5566d77539f.png)
