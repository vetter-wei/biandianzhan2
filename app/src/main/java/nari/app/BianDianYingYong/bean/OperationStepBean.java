package nari.app.BianDianYingYong.bean;

/**
 * 操作步骤类
 * Created by xxxxcl on 2017/12/22 0022.
 */

public class OperationStepBean {
    private String operationOrder = "";
    private String operationDescription = "";
    private String isDone = "";
    private String operationTime = "";


    public String getOperationOrder() {
        return operationOrder;
    }

    public void setOperationOrder(String operationOrder) {
        this.operationOrder = operationOrder;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }
}
