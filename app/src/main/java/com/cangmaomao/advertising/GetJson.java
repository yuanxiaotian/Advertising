package com.cangmaomao.advertising;

import java.util.List;

public class GetJson {

    /**
     * error :
     * obj : {"data":[{"Name":"融腾雷励粤AAX013","pID":10108,"Sel":1,"RVersion":12064179,"ID":10109},{"Name":"雷励-融腾粤AAX013","pID":10107,"Sel":1,"RVersion":12064179,"ID":10108}]}
     * url :
     */

    private String error;
    private ObjBean obj;
    private String url;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ObjBean {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * Name : 融腾雷励粤AAX013
             * pID : 10108
             * Sel : 1
             * RVersion : 12064179
             * ID : 10109
             */

            private String Name;
            private int pID;
            private int Sel;
            private int RVersion;
            private int ID;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public int getPID() {
                return pID;
            }

            public void setPID(int pID) {
                this.pID = pID;
            }

            public int getSel() {
                return Sel;
            }

            public void setSel(int Sel) {
                this.Sel = Sel;
            }

            public int getRVersion() {
                return RVersion;
            }

            public void setRVersion(int RVersion) {
                this.RVersion = RVersion;
            }

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }
        }
    }
}
