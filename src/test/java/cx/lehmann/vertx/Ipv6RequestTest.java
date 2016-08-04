package cx.lehmann.vertx;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class Ipv6RequestTest {

  private static final Logger log = LoggerFactory.getLogger(Ipv6RequestTest.class);

  private Vertx vertx = Vertx.vertx();

  @Test
  public void test(TestContext context) {
    log.info("test starting");
    Async async = context.async();

    HttpClientOptions options = new HttpClientOptions().setDefaultPort(443).setSsl(true);

    HttpClient client = vertx.createHttpClient(options);

    HttpClientRequest req = client.getAbs("https://www.googleapis.com/", resp -> {
      log.info("response code: " + resp.statusCode());
      resp.bodyHandler(data -> {
        log.info("body text: " + data.toString());
        async.complete();
      });
      resp.exceptionHandler(th -> context.fail(th));
    })
        .exceptionHandler(th -> context.fail(th));

    req.end();
  }

}
