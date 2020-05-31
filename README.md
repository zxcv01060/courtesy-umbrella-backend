北商資管系專題 - 愛心傘租借系統 - 後端
===

國立臺北商業大學 - 四技資訊管理系 - 109409組專題(後端)

當前版本：0.0.1

## 後端負責人員

| 姓名 |
|:----:|
|李恩瑋|

### 準備

請開啟CMD(Windows)/Terminal(Mac或Linux)切至專案根目錄，以下說明皆以專案根目錄為出發目錄

1. 請根據您的作業系統下載對應的JDK，如果您已經安裝JDK 11.0.2，則可略過這個步驟

    * [Windows](https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_windows-x64_bin.zip.sha256)
    * [Mac](https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_osx-x64_bin.tar.gz)
    * [Linux](https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_linux-x64_bin.tar.gz)

2. 請在根目錄建立"gradle.properties"檔案，並在裡面新增以下內容(注：此動作可以不用做，如果您已設定JAVA_HOME且為JDK 11.0.2版，您可用 **java -version** 測試是否正確)

    ```
    # 請將{你的11.0.2 JDK根目錄}取代成您自己的JDK根目錄
    # 若使用的是Windows系統，且貼上來的路徑帶有"\"("反斜線")，則須將全部的反斜線變成兩個
    # 如：C:\\Program Files\\Java\\jdk-11.0.2
    org.gradle.java.home={你的11.0.2 JDK根目錄}
    ```

3. 修改客製化設定

    * 請至./src/main/resources下複製application-server.yml，並重新貼上 ，更改檔名為application-local.yml
    * 修改application-local.yml的內容以符合您個人的喜好
    
4. 設定資料庫連線的環境變數(請將{IP}、{PORT}、{username}、{password}修改成本機資料庫連線資訊，若需測試機資訊，請找負責人員索取)

| 變數名稱 | 變數值 | 備註 |
|---------|--------|-----|
|courtesy-umbrella.database.url|jdbc:log4jdbc:mysql://{IP}:{PORT}/sharing_economy?useSSL=false&serverTimezone=Asia/Taipei|資料庫連線字串|
|courtesy-umbrella.database.account|{username}|連線帳號|
|courtesy-umbrella.database.password|{password}|連線密碼|

### 啟動

* Windows

```
$ gradlew.bat bootrun
```
    
* Mac/Linux

```
$ ./gradlew bootrun
```

若伺服器啟動完成，則會在最後一行看到

```
Started CourtesyUmbrellaApplication in xxx (JVM running for xx.xxx)
```

可打開瀏覽器並連線至 **http://localhost:8080/test-started** 確認是否正確啟動

### 打包成Jar檔

* Windows
    
```
$ gradlew.bat build
```

* Max/Linux

```
$ ./gradlew build
```

如果執行失敗，請檢查gradlew檔案是否具有x權限

```
$ ls -l gradlew
# 如果不具有x權限
$ chmod 555 gradlew
# 然後重試
$ ./gradlew build
```

執行完畢後會看見
```
BUILD SUCCESSFUL in xxx
```

可至./build/libs/下找到產生好的courtesy-umbrella-0.0.1.jar
