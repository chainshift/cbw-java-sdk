package com.bix360.wallet.sdk;

import com.alibaba.fastjson.JSONObject;
import com.bix360.wallet.chain.entity.Constants;
import com.bix360.wallet.chain.entity.asset.Explorer;
import com.bix360.wallet.sdk.entity.request.TransferRequest;
import com.bix360.wallet.sdk.entity.request.TxQueryRequest;
import com.bix360.wallet.sdk.entity.request.WithdrawRequest;
import com.bix360.wallet.sdk.entity.response.BixWmResponse;
import com.bix360.wallet.sdk.entity.response.CollectionResult;
import com.bix360.wallet.sdk.entity.response.RepChainTx;
import com.bix360.wallet.sdk.entity.response.RepChainWallet;
import com.bix360.wallet.sdk.entity.response.RepCryptoAsset;
import com.bix360.wallet.sdk.entity.response.RepTenant;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * BIXWM-SDK的使用用例
 * Use cases of BIXWM-SDK
 */
public class BixWmSdkTest {
    /************* Use cases ************/
    protected static class UseCase {
        static Constants.Chain CHAIN = Constants.Chain.ETH;

        static {
            // 使用前需要进行初始化：指定运行环境
            initWithDefault(Constants.Env.DEV);

            // 初始化时可以指定租户密钥
            String tenantKey = "Input your tenant key";
            // initWithTenantKey(Constants.Env.DEV, tenantKey);
            // initWithTenantKey(Constants.Env.PROD, tenantKey);
        }

        /**
         * Init SDK with the specified environment and default tenant key
         * 使用指定的运行环境和默认的租户密钥初始化SDK
         *
         * @param env
         */
        private static void initWithDefault(Constants.Env env) {
            BixWmSdk.init(env);
        }

        /**
         * Init SDK with the specified environment and specified tenant key
         * 使用指定的运行环境和租户密钥初始化SDK
         *
         * @param env
         * @param tenantKey api key of tenant
         */
        private static void initWithTenantKey(Constants.Env env, String tenantKey) {
            BixWmSdk.init(env, tenantKey);
        }

        /**
         * CASE: Load tenant setting
         * 用例: 加载租户的配置信息
         */
        public static void loadTenantSettings() {
            BixWmResponse<RepTenant> tenant = BixWmSdk.inst().loadTenantSettings();
            System.out.println("Finished loadTenantSettings");
            System.out.println("Detail is: " + tenant.toString());
            System.out.println();
        }

        /**
         * CASE: Generate the full url of the explorer by input value and data type
         * 用例: 根据传入的数据类型和值生成完整的区块浏览器访问URL
         */
        public static void getExplorerUrls() {
            String address = "0x0328c3680777b2768b97cc07c4f1ad79ea4ddfb6";
            String tx = "0x46b337bfaebba09a925971b94bf40152a315e263d627c3d350d3c7f4727e52c8";

            String addrUrl = BixWmSdk.inst().explorerURL(CHAIN, Constants.Explorer.ADDRESS, address);
            String txUrl = BixWmSdk.inst().explorerURL(CHAIN, Constants.Explorer.TX, tx, Constants.Lang.ENGLISH);

            System.out.println("Finished getExplorerUrls");
            System.out.println("Detail is: ");
            System.out.println("Address: " + addrUrl);
            System.out.println("Tx: " + txUrl);
            System.out.println();
        }

        /**
         * CASE: Update cold addr
         * 用例: 更新冷钱包地址
         */
        public static void updateColdAddr() {

            String address = "0x0328c3680777b2768b97cc07c4f1ad79ea4ddfb6";
            BixWmResponse<Boolean> booleanBixWmResponse = BixWmSdk.inst().updateColdAddr(CHAIN, address);
            System.out.println(">>> booleanBixWmResponse: " + booleanBixWmResponse);
            String tx = "0x46b337bfaebba09a925971b94bf40152a315e263d627c3d350d3c7f4727e52c8";

            String addrUrl = BixWmSdk.inst().explorerURL(CHAIN, Constants.Explorer.ADDRESS, address);
            String txUrl = BixWmSdk.inst().explorerURL(CHAIN, Constants.Explorer.TX, tx, Constants.Lang.ENGLISH);

            System.out.println("Finished getExplorerUrls");
            System.out.println("Detail is: ");
            System.out.println("Address: " + addrUrl);
            System.out.println("Tx: " + txUrl);
            System.out.println();
        }

        /**
         * CASE: Get tenant's subscribed chains
         * 用例: 获取租户订阅主链列表
         */
        public static void getSubscribedChains() {
            Set<String> chains = BixWmSdk.inst().getSubscribedChains();
            System.out.println("Finished getSubscribedChains");
            System.out.println("Detail is: \n" + Arrays.toString(chains.toArray()));
            System.out.println();
        }

        /**
         * CASE: Get tenant's subscribed chains
         * 用例: 获取租户订阅主链列表
         */
        public static void getAssetTypeMap() {
            Map<String, Set<String>> assetTypeMap = BixWmSdk.inst().getAssetTypeMap();
            System.out.println("Finished getSubscribedChains");
            System.out.println("AssetType Detail is: " + assetTypeMap);
            assetTypeMap.forEach((String name, Set<String> set) -> {
                System.out.println(assetTypeMap.get(name));
            });
            System.out.println();
        }

        /**
         * CASE: Get tenant's subscribed asset map
         * 用例: 获取租户订阅的加密资产列表
         */
        public static void getSubscribedAssetMap() {
            Map<String, Map<String, RepCryptoAsset>> assetMap = BixWmSdk.inst().getSubscribedAssetMap();


            Map<String, RepCryptoAsset> RepCryptoAssetMapTRX = assetMap.get(Constants.Chain.TRX.name());
            Map<String, RepCryptoAsset> RepCryptoAssetMapBTC = assetMap.get(Constants.Chain.BTC.name());

            RepCryptoAsset repCryptoAssetTRX = RepCryptoAssetMapTRX.get(Constants.Chain.TRX.name());
            RepCryptoAsset repCryptoAssetBTC = RepCryptoAssetMapBTC.get(Constants.Chain.BTC.name());
            System.out.println(">>> repCryptoAssetBTC: " + JSONObject.toJSON(repCryptoAssetBTC));
            System.out.println(">>> repCryptoAssetTRX: " + JSONObject.toJSON(repCryptoAssetTRX));
            System.out.println(">>> repCryptoAssetTRX: " + JSONObject.toJSON(repCryptoAssetTRX.getIcon()));

            Explorer explorerTRX = repCryptoAssetTRX.explorer();
            Explorer explorerBTC = repCryptoAssetBTC.explorer();
            System.out.println(">>> explorerTRX: " + explorerTRX);
            System.out.println(">>> explorerBTC: " + explorerBTC);


            System.out.println("Finished getSubscribedAssetMap");
            System.out.println("Detail is: \n" + BixWmSdk.Printer.mapPrinter(assetMap));
            System.out.println("Detail is: \n" + JSONObject.toJSON(assetMap));
            System.out.println();
        }

        /**
         * CASE: Get the block height of specified chain
         * 用例: 获取指定的链的区块高度
         */
        public static void getBlockHeight() {
            BixWmResponse<Long> heightRs = BixWmSdk.inst().getBlockHeight(CHAIN);
            System.out.println("Finished getBlockHeight: " + CHAIN);
            System.out.println("Detail is: " + heightRs.toString());
            System.out.println();
        }

        /**
         * CASE: Create a new address of specified chain
         * 用例: 创建指定的链的地址
         */
        public static void createAddress() {
            String accountName = "SDK-" + UUID.randomUUID().toString();
            BixWmResponse<String> addressRs = BixWmSdk.inst().getOrCreateAddress(CHAIN, accountName);
            System.out.println("Finished createAddress: " + CHAIN);
            System.out.println("Detail is: " + addressRs.toString());
            System.out.println();
        }

        /**
         * CASE: Batch create new address of specified chain
         * 用例: 创建指定的链的地址
         */
        public static void batchCreateAddress() {
            List<String> accountNames = new ArrayList<>();
            accountNames.add("SDK-" + UUID.randomUUID().toString());
            accountNames.add("SDK-" + UUID.randomUUID().toString());

            BixWmResponse<CollectionResult> addressesRs = BixWmSdk.inst().batchCreateAddress(CHAIN, accountNames);
            if (addressesRs.isSuccess()) {
                List<RepChainWallet> createdAddrs = addressesRs.getResult().toList(RepChainWallet.class);
                System.out.println("Finished batchCreateAddress: " + CHAIN);
                System.out.println("Detail is: ");
                System.out.println("Query Result: " + addressesRs.toString());
                System.out.println("Address : " + Arrays.toString(createdAddrs.toArray()));
                System.out.println();
            }
        }

        /**
         * CASE: Subscribe a address of specified chain
         * 用例: 订阅指定的链的地址
         */
        public static void subscribeAddress() {
            String address = "0xAd130fdCDE135549a301Ad027C963664E08f75A8";
            BixWmResponse<String> addressRs = BixWmSdk.inst().subscribeAddress(CHAIN, address);
            System.out.println("Finished subscribeAddress: " + CHAIN);
            System.out.println("Detail is: " + addressRs.toString());
            System.out.println();
        }

        /**
         * CASE: Batch subscribe addresses of specified chain
         * 用例: 批量订阅指定的链的地址
         */
        public static void batchSubscribeAddress() {
            List<String> addresses = new ArrayList<>();
            addresses.add("0xAd130fdCDE135549a301Ad027C963664E08f75A8");
            addresses.add("0xAd442fdCDE135549a301Ad027C963664E08f75A8");

            BixWmResponse<CollectionResult> addressesRs = BixWmSdk.inst().batchSubscribeAddress(CHAIN, addresses);
            List<String> createdAddrs = addressesRs.getResult().toList(String.class);
            System.out.println("Finished batchSubscribeAddress: " + CHAIN);
            System.out.println("Detail is: ");
            System.out.println("Query Result: " + addressesRs.toString());
            System.out.println("Address : " + Arrays.toString(createdAddrs.toArray()));
            System.out.println();
        }

        /**
         * CASE: Transfer to specified address
         * 用例: 提币到指定的地址
         */
        public static void transfer() {
            TransferRequest request = TransferRequest.builder()
                    .symbol("ETH")
                    .addressFrom("0x8f582355b6fa30bab83e5228b285cc2fd646ac3b")
                    .addressTo("0xdf2aE64e659624268704D7c23d83AD0085A812c0")
                    .amount(new BigDecimal("0.001"))
                    .fee(new BigDecimal("0.0001"))
                    .outBizNo(WithdrawRequest.gainBizNo())
                    .isSync(true)
                    .remark("TransferSample")
                    .build();
            BixWmResponse<String> operationRs = BixWmSdk.inst().transfer(CHAIN, request);
            System.out.println("Finished transfer: " + CHAIN);
            System.out.println("Detail is: " + operationRs.toString());
            System.out.println();
        }

        /**
         * CASE: Withdraw to specified address
         * 用例: 提币到指定的地址
         */
        public static void withdraw() {
            WithdrawRequest request = WithdrawRequest.builder()
                    .symbol("ETH")
                    .addressTo("0xAd130fdCDE135549a301Ad027C963664E08f75A8")
                    .amount(new BigDecimal("0.01"))
                    .fee(new BigDecimal("0.001"))
                    .isSync(false)
                    .outBizNo(WithdrawRequest.gainBizNo())
                    .remark("WithdrawSample")
                    .build();
            BixWmResponse<String> operationRs = BixWmSdk.inst().withdraw(CHAIN, request);
            System.out.println("Finished withdraw: " + CHAIN);
            System.out.println("Detail is: " + operationRs.toString());
            System.out.println();
        }

        /**
         * CASE: Query balance detail of specified crypto
         * 用例: 获取指定的加密资产的余额详情
         */
        public static void queryCryptoBalance() {
            String symbol = "ETH";
            BixWmResponse<BigDecimal> balanceRs = BixWmSdk.inst().queryCryptoBalance(CHAIN, symbol);
            System.out.println("Finished queryCryptoBalance: " + CHAIN);
            System.out.println("Detail is: " + balanceRs.toString());
            System.out.println();
        }

        /**
         * CASE: Query crypto balance detail of specified adderss
         * 用例: 获取指定地址的加密资产的余额详情
         */
        public static void queryAddressBalance() {
            String address = "0xAd130fdCDE135549a301Ad027C963664E08f75A8";
            List<String> symbols = new ArrayList<>();
            symbols.add("SS");
            symbols.add("ETH");

            BixWmResponse<String> heightRs = BixWmSdk.inst().queryAddressBalance(CHAIN, address, symbols);
            System.out.println("Finished queryAddressBalance: " + CHAIN);
            System.out.println("Detail is: " + heightRs.toString());
            System.out.println();
        }

        /**
         * CASE: Query detail of chain tx
         * 用例: 获取链上交易详情
         */
        public static void queryTx() {
            String txId = "0x35fd82d0d84125d1fa74e1c06ceb571104d487aad25d8bb4fbee54f4b4fcc9cd";
            BixWmResponse<JSONObject> queryRs = BixWmSdk.inst().queryTx(CHAIN, txId);
            System.out.println("Finished queryTx: " + CHAIN);
            System.out.println("Detail is: " + queryRs.toString());
            System.out.println();
        }

        /**
         * CASE: Query detail of chain txs
         * 用例: 获取链上交易列表详情
         */
        public static void queryTxList(Constants.Tx type) {
            TxQueryRequest txQuery = TxQueryRequest.builder()
                    /** 其他交易类型请查阅{@link Constants.Tx} */
                    .type(type)
                    .address("0x8f582355b6fa30bab83e5228b285cc2fd646ac3b")
                    /** 可以指定查询的起始区块高度 */
                    .startHeight(null)
                    .build();
            txQuery.addSymbol("ETH").addSymbol("USDT");

            BixWmResponse<CollectionResult> queryRs = BixWmSdk.inst().queryTxList(CHAIN, txQuery);
            List<RepChainTx> chainTxs = queryRs.getResult().toList(RepChainTx.class);
            System.out.println("Finished queryTxList: " + CHAIN);
            System.out.println("Detail is: ");
            System.out.println("Query Result: " + queryRs.toString());
            System.out.println("Chain txs: " + Arrays.toString(chainTxs.toArray()));
            System.out.println();
        }
    }

    public static void main(String[] args) {
        /**
         *  使用方法：请查看头部的类说明 {@link BixWmSdk}
         *  How to use: view the class description at head of this file {@link BixWmSdk}
         *
         *  更多用例请查看和运行{@link UseCase}
         *  More cases please view and execute the {@link UseCase}
         */
        // PASSED TEST-CASEs:
        UseCase.loadTenantSettings();
        //        UseCase.getExplorerUrls();

        //        UseCase.getSubscribedChains();
        //        UseCase.getSubscribedAssetMap();
        //        UseCase.getAssetTypeMap();

        //        UseCase.getBlockHeight();
        //        UseCase.updateColdAddr();
        //        UseCase.createAddress();
        //        UseCase.batchCreateAddress();
        //        UseCase.subscribeAddress();
        //        UseCase.batchSubscribeAddress();
        UseCase.queryAddressBalance();
        //        UseCase.queryCryptoBalance();
        //        UseCase.transfer();
        //        UseCase.withdraw();
        //        UseCase.queryTx();
        //        UseCase.queryTxList(Constants.Tx.DEPOSIT);
        //        UseCase.queryTxList(Constants.Tx.TRANSFER);
        //        UseCase.queryTxList(Constants.Tx.WITHDRAW);
        //        UseCase.queryCryptoBalance();
    }
}
