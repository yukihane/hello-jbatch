# これは何?

jBatchを試しに実行してみた際のサンプルコード.

[java - CDIの@RequestScopedで作成したビジネスロジックをjBatchから呼び出す方法 - スタック・オーバーフロー](https://ja.stackoverflow.com/q/34920/2808) の詳細を見る目的で作成.

https://github.com/javaee-samples/javaee7-samples/tree/master/batch/batchlet-simple
のソースを改変して作成.

1. [3b3047fc67](https://github.com/yukihane/hello-jbatch/commit/3b3047fc67bd0eb9b64592a5235cd37e10d3638c) でjBatch動作確認.
1. [e3d8ededcc](https://github.com/yukihane/hello-jbatch/commit/e3d8ededcc73e57a5ffe51ea8e510815dedd6d5a) で`RequestScoped`をinjectすると例外が発生することを確認.
1. [13ef3b2d19](https://github.com/yukihane/hello-jbatch/commit/13ef3b2d1934cfcc7ffbea6a3ac22725d244ee42) で自前でrequest contextをアクティベートし想定通り動作する(例外が発生しなくなる)ことを確認.

ちなみに, 発生する例外は次のようなもの:


    10:15:01,645 WARN  [org.jberet] (Batch Thread - 3) JBERET000001: Failed to run batchlet org.jberet.job.model.RefArtifact@730db889: org.jboss.weld.context.ContextNotActiveException: WELD-001303: No active contexts for scope type javax.enterprise.context.RequestScoped
        at org.jboss.weld.manager.BeanManagerImpl.getContext(BeanManagerImpl.java:689)
        at org.jboss.weld.bean.ContextualInstanceStrategy$DefaultContextualInstanceStrategy.getIfExists(ContextualInstanceStrategy.java:90)
        at org.jboss.weld.bean.ContextualInstanceStrategy$CachingContextualInstanceStrategy.getIfExists(ContextualInstanceStrategy.java:165)
        at org.jboss.weld.bean.ContextualInstance.getIfExists(ContextualInstance.java:63)
        at org.jboss.weld.bean.proxy.ContextBeanInstance.getInstance(ContextBeanInstance.java:83)
        at org.jboss.weld.bean.proxy.ProxyMethodHandler.getInstance(ProxyMethodHandler.java:125)
        at jp.himeji_cs.javaee.hello_jbatch.MyRequestScopedBean$Proxy$_$$_WeldClientProxy.sayHello(Unknown Source)
        at org.javaee7.batch.batchlet.simple.MyBatchlet.process(MyBatchlet.java:61)
        at org.jberet.runtime.runner.BatchletRunner.run(BatchletRunner.java:72)
        at org.jberet.runtime.runner.StepExecutionRunner.runBatchletOrChunk(StepExecutionRunner.java:229)
        at org.jberet.runtime.runner.StepExecutionRunner.run(StepExecutionRunner.java:147)
        at org.jberet.runtime.runner.CompositeExecutionRunner.runStep(CompositeExecutionRunner.java:164)
        at org.jberet.runtime.runner.CompositeExecutionRunner.runFromHeadOrRestartPoint(CompositeExecutionRunner.java:88)
        at org.jberet.runtime.runner.JobExecutionRunner.run(JobExecutionRunner.java:60)
        at org.wildfly.extension.batch.jberet.impl.BatchEnvironmentService$WildFlyBatchEnvironment$1.run(BatchEnvironmentService.java:243)
        at org.wildfly.extension.requestcontroller.RequestController$QueuedTask$1.run(RequestController.java:497)
        at org.jberet.spi.JobExecutor$3.run(JobExecutor.java:161)
        at org.jberet.spi.JobExecutor$1.run(JobExecutor.java:99)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
        at java.lang.Thread.run(Thread.java:745)
        at org.jboss.threads.JBossThread.run(JBossThread.java:320)



# 実行環境

WildFly10.1.0.Final (jBatch実装: jberet 1.2.1, CDI実装: Weld 2.3.5)

自前request contextアクティベートについてはWeld依存, それ以外は実装非依存の想定.

# ビルド, 実行方法

    mvn clean package


生成された `hello-jbatch-0.1.0-SNAPSHOT.war` をアプリケーションサーバにデプロイし
http://localhost:8080/hello-jbatch-0.1.0-SNAPSHOT/rest/execute
へアクセスする.
