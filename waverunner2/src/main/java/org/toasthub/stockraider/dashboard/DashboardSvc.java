package org.toasthub.stockraider.dashboard;

import org.toasthub.utils.Request;
import org.toasthub.utils.Response;

public interface DashboardSvc {
	public void process(Request request, Response response);
	public void getData(Request request, Response response);
}
