/*
 * Copyright (c) 2015 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * openAUSIAS: The stunning micro-library that helps you to develop easily 
 * AJAX web applications by using Java and jQuery
 * openAUSIAS is distributed under the MIT License (MIT)
 * Sources at https://github.com/rafaelaznar/openAUSIAS
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */
package net.daw.service.specific.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.specific.implementation.PreguntaBean;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.dao.specific.implementation.PreguntaDao;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.ParameterCook;
import net.daw.service.generic.implementation.TableServiceGenImpl;

/**
 *
 * @author juliomiguel
 */
public class PreguntaService extends TableServiceGenImpl{
 
     public PreguntaService(HttpServletRequest request) {
        super(request);
    }
 
    @Override
    public String getall() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();

        PreguntaDao oPreguntaDao = new PreguntaDao(oConnection);
        ArrayList<PreguntaBean> alPreguntaBean = new ArrayList<PreguntaBean>();
        ArrayList<FilterBeanHelper> alFilterBeanHelper = ParameterCook.prepareFilter(oRequest);
        HashMap<String, String> hmOrder = ParameterCook.prepareOrder(oRequest);
        
        alPreguntaBean = oPreguntaDao.getAll(alFilterBeanHelper, hmOrder);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd/MM/yyyy");
        Gson gson = gsonBuilder.create();
        String data = gson.toJson(alPreguntaBean);

        return data;
    }
    
     @Override
    public String getpage() throws Exception {

        Connection oConnection = null;

        oConnection = new BoneConnectionPoolImpl().newConnection();

        PreguntaDao oPreguntaDao = new PreguntaDao(oConnection);

        ArrayList<PreguntaBean> oPreguntaArray = new ArrayList<>();

        //Obtenemos parámetros con el ParameterCook
        int rpp = ParameterCook.prepareRpp(oRequest);

        int page = ParameterCook.preparePage(oRequest);

        //ArrayList para sacar los filtros
        ArrayList<FilterBeanHelper> alFilterBeanHelper = ParameterCook.prepareFilter(oRequest);

        //HashMap para sacar el orden
        HashMap<String, String> hmOrder = ParameterCook.prepareOrder(oRequest);

        //Asignamos getPage(con sus parámetros) al ArrayList oProfesorArray
        oPreguntaArray = oPreguntaDao.getPage(rpp, page, alFilterBeanHelper, hmOrder);

        //Creamos el Json para mostrarlo en pantalla
        Gson oGson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        return oGson.toJson(oPreguntaArray);

    }
     
}
