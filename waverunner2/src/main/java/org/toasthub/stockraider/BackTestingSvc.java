package org.toasthub.stockraider;

import org.toasthub.utils.Request;
import org.toasthub.utils.Response;


public interface BackTestingSvc {
    public void defaultBackTest(Request request, Response response);
}
