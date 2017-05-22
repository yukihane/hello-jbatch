# これは何?

jBatchを試しに実行してみた際のサンプルコード.

https://github.com/javaee-samples/javaee7-samples/tree/master/batch/batchlet-simple
のソースを改変して作成.

[java - CDIの@RequestScopedで作成したビジネスロジックをjBatchから呼び出す方法 - スタック・オーバーフロー](https://ja.stackoverflow.com/q/34920/2808) の詳細を見る目的で作成.

# ビルド, 実行方法

    mvn clean package


生成された `hello-jbatch-0.1.0-SNAPSHOT.war` をアプリケーションサーバにデプロイし
http://localhost:8080/hello-jbatch-0.1.0-SNAPSHOT/rest/execute
へアクセスする(WildFlyの場合).
