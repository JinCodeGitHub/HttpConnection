Http Connection
==========

The Asynchronous JAVA HTTP Connection For Android.
* HttpConnection Class & HttpConnectionHandler Interface

Contents
==============================

1. <a href="#1-detail--contact">Detail & Contact</a>
2. <a href="#2-how-to-use">How To Use</a>
3. <a href="#3-license">License</a>

1. Detail & Contact
==============================

* Name: Http Connection
* Version: 1.0
* Author: Misam Saki, http://misam.ir/
* Document: https://github.com/misamplus/HttpConnection

2. How To Use
==============================

1. Add HttpConnection.java to your code.
2. Create a new HttpConnection class.
   <pre>
    HttpConnection httpConnection = new HttpConnection("[url and get parameters]", ...
   </pre>
3. Now you must add a new HttpConnectionHandler.
  	<pre>
     HttpConnection httpConnection = new HttpConnection("[url and get parameters]", new HttpConnectionHandler() {
            @Override
            public void onStart() {
              // Your codes which want to run before execute connection
            }

            @Override
            public void onFinish(String response) {
              // Your codes which want to run after execute connection.
              // You can use 'response' string in your codes.
            }

            @Override
            public void onFault(String response, String error) {
              // Your codes which want to run after fault connection.
              // You can use 'response' and 'error' strings in your codes.
            }
    });
    </pre>
4. (Option) You can add post parameters.
    <pre>
    httpConnection.addPostParameters("[key]", "[value]");
    ...
    </pre>
    (Option) or set post parameters manually.
    <pre>
    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    postParameters.add(new BasicNameValuePair("[key]", "[value]"));
    ...
    httpConnection.setPostParameters(postParameters);
    </pre>
5. Execute your connection.
    <pre>
    httpConnection.execute();
    </pre>

3. License
==============================

Copyright © 2000 Misam Saki <hi@misam.ir>

This work is free. You can redistribute it and/or modify it under the

terms of the Do What The Fuck You Want To Public License, Version 2,

as published by Sam Hocevar. See the COPYING file for more details.
