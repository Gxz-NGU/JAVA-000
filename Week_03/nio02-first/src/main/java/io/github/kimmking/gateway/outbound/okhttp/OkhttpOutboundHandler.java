package io.github.kimmking.gateway.outbound.okhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.util.EntityUtils;

import static  io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;

import java.io.IOException;

public class OkhttpOutboundHandler {

    private OkHttpClient  okHttpClient;
    private String backendUrl;

    public OkhttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl.endsWith("/")?backendUrl.substring(0,backendUrl.length()-1):backendUrl;
        this.okHttpClient = new OkHttpClient();
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        final String url = this.backendUrl + fullRequest.uri();
        fetchGet(fullRequest,ctx,url);
    }

    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        Request request =new Request.Builder().url(url).build();
        try ( Response response = okHttpClient.newCall(request).execute()) {
            // 这里的response.body().string(),因为用一次就会把里面的流清空
//            System.out.println(response.body().string());
            handleResponse(inbound,ctx,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final Response okhttpResponse) {
        FullHttpResponse  response = null;
        try {
            response = new DefaultFullHttpResponse(HTTP_1_1,OK, Unpooled.wrappedBuffer(okhttpResponse.body().bytes()));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length",Integer.parseInt(okhttpResponse.header("Content-Length")));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)){
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }

}
