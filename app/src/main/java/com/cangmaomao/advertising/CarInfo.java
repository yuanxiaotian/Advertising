package com.cangmaomao.advertising;

import java.util.List;

public class CarInfo {


    /**
     * error :
     * obj : {"data":[{"Name":"仁捷车队-仁捷车队-粤ABM418、ABU146、ABS145、","pID":"org-10090","Sel":1,"Type":0,"ID":"org-10731"},{"Name":"仁捷车队-粤ABM418、ABU146、ABS145、ABU44","pID":"org-10731","Sel":1,"Type":0,"ID":"org-10730"},{"Name":"粤ABS145","v":0,"pID":"org-10730","Sel":1,"Type":1,"ID":46626,"on":1},{"Name":"粤ABU146","v":0,"pID":"org-10730","Sel":1,"Type":1,"ID":47546,"on":1},{"Name":"粤ABM418","v":0,"pID":"org-10730","Sel":1,"Type":1,"ID":47544,"on":1},{"Name":"粤ABM428","v":0,"pID":"org-10730","Sel":1,"Type":1,"ID":48624,"on":1},{"Name":"粤ABU445","v":0,"pID":"org-10730","Sel":1,"Type":1,"ID":46615,"on":1}],"hasTrailer":0}
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
        /**
         * data : [{"Name":"仁捷车队-仁捷车队-粤ABM418、ABU146、ABS145、","pID":"org-10090","Sel":1,"Type":0,"ID":"org-10731"},{"Name":"仁捷车队-粤ABM418、ABU146、ABS145、ABU44","pID":"org-10731","Sel":1,"Type":0,"ID":"org-10730"},{"Name":"粤ABS145","v":0,"pID":"org-10730","Sel":1,"Type":1,"ID":46626,"on":1},{"Name":"粤ABU146","v":0,"pID":"org-10730","Sel":1,"Type":1,"ID":47546,"on":1},{"Name":"粤ABM418","v":0,"pID":"org-10730","Sel":1,"Type":1,"ID":47544,"on":1},{"Name":"粤ABM428","v":0,"pID":"org-10730","Sel":1,"Type":1,"ID":48624,"on":1},{"Name":"粤ABU445","v":0,"pID":"org-10730","Sel":1,"Type":1,"ID":46615,"on":1}]
         * hasTrailer : 0
         */

        private int hasTrailer;
        private List<DataBean> data;

        public int getHasTrailer() {
            return hasTrailer;
        }

        public void setHasTrailer(int hasTrailer) {
            this.hasTrailer = hasTrailer;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * Name : 仁捷车队-仁捷车队-粤ABM418、ABU146、ABS145、
             * pID : org-10090
             * Sel : 1
             * Type : 0
             * ID : org-10731
             * v : 0
             * on : 1
             */

            private String Name;
            private String pID;
            private int Sel;
            private int Type;
            private String ID;
            private int v;
            private int on;

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getPID() {
                return pID;
            }

            public void setPID(String pID) {
                this.pID = pID;
            }

            public int getSel() {
                return Sel;
            }

            public void setSel(int Sel) {
                this.Sel = Sel;
            }

            public int getType() {
                return Type;
            }

            public void setType(int Type) {
                this.Type = Type;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public int getV() {
                return v;
            }

            public void setV(int v) {
                this.v = v;
            }

            public int getOn() {
                return on;
            }

            public void setOn(int on) {
                this.on = on;
            }
        }
    }
}
