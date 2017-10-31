package com.cncf.android.span_trace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.common.collect.ImmutableMap
import com.uber.jaeger.Configuration
import com.uber.jaeger.Configuration.ReporterConfiguration
import com.uber.jaeger.Configuration.SamplerConfiguration
import com.uber.jaeger.Tracer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "Tracer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_click_me.setOnClickListener {

            val helloTo = "johnny bravo"

            Toast.makeText(getApplicationContext(),
                    "Span initiated", Toast.LENGTH_SHORT).show()

            //tracer instantiation
            fun initTracer(service: String): com.uber.jaeger.Tracer {
                val samplerConfig = SamplerConfiguration("const", 1)
                val reporterConfig = ReporterConfiguration(true, null, null, null, null)
                val config = Configuration(service, samplerConfig, reporterConfig)
                return config.tracer as com.uber.jaeger.Tracer
            }

            val tracer: Tracer =  initTracer("hello-world")

            Log.d(TAG, "Tracer successfully initialized!")

            val span = tracer.buildSpan("say-hello").startManual()

            span.setTag("hello-to", helloTo)

            val helloStr = String.format("Hello, %s!", helloTo)
            span.log(ImmutableMap.of("event", "string-format", "value", helloStr))

            println(helloStr)
            span.log(ImmutableMap.of("event", "println"))

            span.finish()

            tracer.close()
        }


    }
}