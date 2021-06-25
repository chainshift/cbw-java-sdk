# BIXWM SDK

本文档主要阐述如何使用BIXWM-SDK来使用BIXWM提供的加密资产的托管服务。

## 接入步骤
1. 请查看[BIXWM-API](../cbw-api/README.md)申请租户的ApiKey。
2. 将`bixwm-sdk.jar`添加到本地工程中，详见[工程依赖](#工程依赖)。
3. 参见[使用说明](#使用说明)调用。

## 工程依赖
- ① 本地工程依赖
```text
    1. 下载本仓库中的bixwm-sdk.jar和lib文件夹下的所有jar包到本地
    2. 将下载的bixwm-sdk.jar和其余的jar导入本地工程中
    3. bixwm-sdk按照单独的版本设置了文件夹，文件夹名即是版本号
```

- ② Maven方式依赖：将以下Maven设置添加到本地工程的pom中
```xml
    <repositories>
        <repository>
            <id>bixwm-repo</id>
            <name>bixwm repo</name>
            <url>http://nexus.ichaoj.com/repository/nexus-releases/</url>
        </repository>
    </repositories>
    <dependency>
        <groupId>com.bix360.wallet</groupId>
        <artifactId>bixwm-sdk</artifactId>
        <version>1.0.0</version>
    </dependency>
```

## 使用说明
1. 初始化SDK：设置访问的环境和你的租户Key
```java
    Constants.Env env = Constants.Env.PROD;
    String tenantKey = "your tenant key";
    BixWmSdk.init(Constants.Env.PROD, tenantKey);
```
2. 直接调用`BixWmSdk.inst().$方法名`即可访问和使用相关方法
```java
    Constants.Chain chain = Constants.Chain.ETH;
    String accountName = "account name";
    BixWmResponse<String> response = BixWmSdk.inst().getOrCreateAddress(chain, accountName);
    String onChainAddr =  response.getResult();
```

更多的方法说明详情请参见[BixWmSdkTest](src/test/java/com/bix360/wallet/sdk/BixWmSdkTest.java)里的用例

## API列表
请查看[BIXWM-API](../cbw-api/README.md)