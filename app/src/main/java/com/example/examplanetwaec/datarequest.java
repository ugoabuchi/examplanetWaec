package com.example.examplanetwaec;
/****************************************************************************************
 This project app was proudly developed by the app_dev team and several educational professionals of
 MAckIV Consult which is a parent company in which Examplanet Services fall under, and all components relating to this
 app should not be reused or duplicated by any external bodies without the prior approval of MackIV Consult.
 Copyright 2020.
 Signed: Management
 Director: Tawede Kehinde
 General Supervisor: Ajayi BabaTosin
 Project Supervisor: Tosere Ojeme (Head of IT)
 Lead Software Developer 1: Mathew Fortune
 Lead Software Developer 2: Oladapo Yusuf
 Blog Developer and Administrator: Oke OluwaTimileyin
 UI/UX: Olawale Damilola
 ***************************************************************************************/
// Data Request Class for handling all Server request

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class datarequest {
    Context context;
    customListener listener = null;
    RequestQueue requestQueue;
    String[] res = new String[2];
    ProgressDialog dialog = null;


    datarequest(Context context, customListener listener) {
        this.context = context;
        this.listener = listener;
        requestQueue = Volley.newRequestQueue(context);
        dialog = ProgressDialog.show(this.context, "processing", "Please wait...", true);
        dialog.dismiss();
    }



    //Server returns a string value of the total question set by facilitator relating to a topic
    public void getQuestionTotal(final String topicId) {
    dialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String URL = new constants().getAddress() + "/waec";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                res[0] = "success";
                res[1] = response;
                listener.onResponse(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                res[0] = "error";
                res[1] = error.getLocalizedMessage();
                listener.onResponse(res);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("view", "questionnos");
                params.put("topicId", topicId);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        requestQueue.add(stringRequest);
    }

    //server return a string json array value of the total question set by facilitator relating to a topic
    public void getQuestions(final String topicId) {
    dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String URL = new constants().getAddress() + "/waec";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                res[0] = "success";
                res[1] = response;
                listener.onResponse(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                res[0] = "error";
                res[1] = error.getLocalizedMessage();
                listener.onResponse(res);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("view", "getquestions");
                params.put("topicId", topicId);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        requestQueue.add(stringRequest);
    }


    public void getExamQuestions(final String subjectId, final String qnos) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String URL = new constants().getAddress() + "/waec";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                res[0] = "success";
                res[1] = response;
                listener.onResponse(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                res[0] = "error";
                res[1] = error.getLocalizedMessage();
                listener.onResponse(res);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("view", "getExamQuestions");
                params.put("subjectId", subjectId);
                params.put("qnos", qnos);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        requestQueue.add(stringRequest);
    }



    //server return a string json array value of the question requested
    public void getTopics(final String subjectid) {

        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String URL = new constants().getAddress() + "/waec";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                res[0] = "success";
                res[1] = response;
                listener.onResponse(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                res[0] = "error";
                res[1] = error.getLocalizedMessage();
                listener.onResponse(res);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("view", "topics");
                params.put("subjectid", subjectid);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        requestQueue.add(stringRequest);
    }


    //server return a string json array value of the question requested
    public void glogin(final String email) {

        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String URL = new constants().getAddress() + "/waec";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                res[0] = "success";
                res[1] = response;
                listener.onResponse(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                res[0] = "error";
                res[1] = error.getLocalizedMessage();
                listener.onResponse(res);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("view", "glogin");
                params.put("email", email);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        requestQueue.add(stringRequest);
    }



    //server return a string json array value of the question requested
    public void sblog(final String termid) {

        //dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String URL = new constants().getAddress() + "/waec";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //dialog.dismiss();
                res[0] = "success";
                res[1] = response;
                listener.onResponse(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //dialog.dismiss();
                res[0] = "error";
                res[1] = error.getLocalizedMessage();
                listener.onResponse(res);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("view", "getblogpreview");
                params.put("term_id", termid);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        requestQueue.add(stringRequest);
    }


    //server return a string json array value of the question requested
    public void blog(final String termid, final String offset) {

        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String URL = new constants().getAddress() + "/waec";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                res[0] = "success";
                res[1] = response;
                listener.onResponse(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                res[0] = "error";
                res[1] = error.getLocalizedMessage();
                listener.onResponse(res);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("view", "getblog");
                params.put("term_id", termid);
                params.put("offset", offset);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        requestQueue.add(stringRequest);
    }



    //server return a string json array value of the question requested
    public void blognext(final String termid, final String offset) {

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String URL = new constants().getAddress() + "/waec";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                res[0] = "success";
                res[1] = response;
                listener.onResponse(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                res[0] = "error";
                res[1] = error.getLocalizedMessage();
                listener.onResponse(res);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("view", "getblog");
                params.put("term_id", termid);
                params.put("offset", offset);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        requestQueue.add(stringRequest);
    }


    public void emailExist(final String email)
    {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String URL = new constants().getAddress() + "/waec";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                res[0] = "success";
                res[1] = response;
                listener.onResponse(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                res[0] = "error";
                res[1] = error.getLocalizedMessage();
                listener.onResponse(res);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("view", "checkemail");
                params.put("email", email);


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        requestQueue.add(stringRequest);
    }

    //server return a string json array value of the question requested
    public void signup(final String name, final String email, final String passcode, final String gender, final String dob, final String picture) {

        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String URL = new constants().getAddress() + "/waec";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                res[0] = "success";
                res[1] = response;
                listener.onResponse(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                res[0] = "error";
                res[1] = error.getLocalizedMessage();
                listener.onResponse(res);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("view", "signup");
                params.put("email", email);
                params.put("passcode", passcode);
                params.put("name", name);
                params.put("gender", gender);
                params.put("dob", dob);
                params.put("picture", picture);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        requestQueue.add(stringRequest);
    }

    public void verifyEmail(final String email)
    {
        dialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(this.context);
            String URL = new constants().getAddress() + "/waec";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();
                    res[0] = "success";
                    res[1] = response;
                    listener.onResponse(res);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    res[0] = "error";
                    res[1] = error.getLocalizedMessage();
                    listener.onResponse(res);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("view", "verify");
                    params.put("email", email);


                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }

            };

            requestQueue.add(stringRequest);

    }







}
