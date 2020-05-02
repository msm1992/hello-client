package com.example.tracing.hello.client;

import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

public class InitTracer {
    public void init() {
        Configuration.SamplerConfiguration samplerConfig = new Configuration.SamplerConfiguration()
                .withType("const")
                .withParam(1);
        Configuration.SenderConfiguration senderConfig = new Configuration.SenderConfiguration()
                .withAgentHost("localhost")
                .withAgentPort(5775);
        Configuration.ReporterConfiguration reporterConfig = new Configuration.ReporterConfiguration()
                .withLogSpans(true)
                .withFlushInterval(1000)
                .withMaxQueueSize(1000)
                .withSender(senderConfig);

        Tracer tracer = new Configuration("apim-client").withSampler(samplerConfig)
                .withReporter(reporterConfig).getTracer();
        GlobalTracer.register(tracer);
    }
}
