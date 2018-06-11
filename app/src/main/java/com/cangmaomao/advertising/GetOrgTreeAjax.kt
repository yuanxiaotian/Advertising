package com.cangmaomao.advertising

class GetOrgTreeAjax {

    /**
     * error :
     * obj : {"data":[{"Name":"融腾雷励粤AAX013","pID":10108,"Sel":1,"RVersion":12064179,"ID":10109},{"Name":"雷励-融腾粤AAX013","pID":10107,"Sel":1,"RVersion":12064179,"ID":10108}]}
     * url :
     */

    private var error: String? = null
    private var obj: ObjBean? = null
    private var url: String? = null


    class ObjBean {
        var data: List<DataBean>? = null

        class DataBean {
            /**
             * Name : 融腾雷励粤AAX013
             * pID : 10108
             * Sel : 1
             * RVersion : 12064179
             * ID : 10109
             */

            var name: String? = null
            var pid: Int = 0
            var sel: Int = 0
            var rVersion: Int = 0
            var id: Int = 0
        }
    }
}