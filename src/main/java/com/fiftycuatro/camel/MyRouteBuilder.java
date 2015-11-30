package com.fiftycuatro.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {

        // here is a sample which processes the input files
        // (leaving them in place - see the 'noop' flag)
        // then performs content based routing on the message using XPath
        from("file:src/data?noop=true")
                    .log("Message Received")
                    .process(new Processor() {
                        public void process(Exchange in) {
                           //Calculate FTP Path
                           in.getIn().setHeader("OutputFtpPath", "sftp://localhost:22/some/path/new?username=vagrant&password=vagrant&disconnect=true");
                        }
                     })
                    .to("log:com.fiftycuatro.ftp?level=INFO&showHeaders=true")
                    .log("${headers.OutputFtpPath}")
                    .recipientList(simple("${headers.OutputFTPPath}"));
    }

}
