package org.toasthub.stockraider.analysis;

import org.toasthub.utils.Request;
import org.toasthub.utils.Response;


public interface BackTestingSvc {
    public void process(Request request, Response response);
}
