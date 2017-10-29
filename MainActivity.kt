package com.cncf.android.span_trace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.opentracing.util.GlobalTracer
import kotlinx.android.synthetic.main.activity_main.*

class Hello  constructor( val tracer: io.opentracing.Tracer) {


    fun sayHello(helloTo: String) {
        val span = tracer.buildSpan("say-hello").startManual()

        val helloStr = String.format("Hello, %s!", helloTo)
        print(helloStr)
        span.finish()


    }

}


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_click_me.setOnClickListener {

             val helloTo = "johnny bravo"
             Hello(GlobalTracer.get()).sayHello(helloTo)
            Toast.makeText(getApplicationContext(),
                    "Span initiated", Toast.LENGTH_SHORT).show()


        }

    }
}