package nari.app.BianDianYingYong.bean;

/**
 * 操作步骤记录类
 * Created by xxxxcl on 2017/12/22 0022.
 */

public class OperationStepReacords {
    private OperationStepBean[] operationStepBeanReacords;

    public OperationStepReacords(String operationStepReacordsString){
        String[] recordStrings = operationStepReacordsString.split("@@");
        int count = recordStrings.length;
        operationStepBeanReacords = new OperationStepBean[count];
        for(int i = 0;i<count;i++ ){
            String eachrecordString = recordStrings[i];
            String[] recordFristSplit = eachrecordString.split("|");
            String order = recordFristSplit[0];
            String description =recordFristSplit[1];
            String isDone = recordFristSplit[2];
            String time = recordFristSplit[3];
            //装载数据
            OperationStepBean operationStepBean = new OperationStepBean();
            operationStepBean.setOperationOrder(order);
            operationStepBean.setOperationDescription(description);
            operationStepBean.setIsDone(isDone);
            operationStepBean.setOperationTime(time);
            operationStepBeanReacords[i] = operationStepBean;
        }
    }


    public OperationStepBean[] getOperationStepBeanReacords() {
        return operationStepBeanReacords;
    }

    public void setOperationStepBeanReacords(OperationStepBean[] operationStepBeanReacords) {
        this.operationStepBeanReacords = operationStepBeanReacords;
    }
}
