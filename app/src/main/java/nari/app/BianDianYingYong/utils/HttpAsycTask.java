package nari.app.BianDianYingYong.utils;

import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nari.mip.util.rpc.Response;

import static android.R.attr.id;
import static android.app.Activity.RESULT_OK;
import static nari.mip.core.a.a.p;
import static nari.mip.core.a.a.s;

/**
 * Created by ShawDLee on 2018/1/24.
 */

public class HttpAsycTask extends AsyncTask<ParamBean,Integer,String> {
    public interface DataChuli {
        void chuli(String data);
    }
    DataChuli dataChuli;

    public void setDataChuli(DataChuli dataChuli) {
        this.dataChuli = dataChuli;
    }

    @Override
    protected String doInBackground(ParamBean... paramBeen) {
        ParamBean p = paramBeen[0];
        String data = DataReadUtil.getDataFromDb(p.getService(), p.getIntfacre(), new Object[]{p.getParams()} );
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(dataChuli != null){
            dataChuli.chuli(s);
        }

    }
}