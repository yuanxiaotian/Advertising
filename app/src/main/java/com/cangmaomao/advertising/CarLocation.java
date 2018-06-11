package com.cangmaomao.advertising;

import java.util.List;

public class CarLocation {

    /**
     * error :
     * obj : {"lastTime":1528428240835,"data":[{"DriverB":"","Odometer":19276.900390625,"recvTime":1528427442000,"DeviceNo":6258129,"TrailerStatus":0,"ExStatusStr":"","Direction":86,"PointInfo":"","TrailerName":"","Driver":"","Oil":"0.0","T4":"0","OffsetLat":23.159047290119855,"DriverBPhone":"","Organ":10730,"AlarmStatus":"","RoadName":"","Lon":113.495285,"OffsetLon":113.50061113109118,"ExStatus":0,"Lat":23.1617,"SN":0,"DriverPhone":"","GPSStatus":"ACC开,定位,天线正常","T1":"0","T3":"0","GpsTime":1528428246000,"T2":"0","ExceptionStatus":"","Status":65536,"Speed":0,"RegName":"粤ABS145","SimCard":"14716258129","StatusStr":"11100010101001","linkStatus":1,"IsTrailer":0,"PlaceName":"","VehicleID":46626},{"DriverB":"","Odometer":44041.5,"recvTime":1528427395000,"DeviceNo":6418485,"TrailerStatus":0,"ExStatusStr":"","Direction":198,"PointInfo":"","TrailerName":"","Driver":"","Oil":"0.0","T4":"0","OffsetLat":23.101777556172298,"DriverBPhone":"","Organ":10730,"AlarmStatus":"","RoadName":"","Lon":113.53495,"OffsetLon":113.54013812057802,"ExStatus":0,"Lat":23.104567,"SN":0,"DriverPhone":"","GPSStatus":"ACC开,定位,天线正常","T1":"0","T3":"0","GpsTime":1528428225000,"T2":"0","ExceptionStatus":"","Status":65536,"Speed":0,"RegName":"粤ABU146","SimCard":"14716418485","StatusStr":"11100010101001","linkStatus":1,"IsTrailer":0,"PlaceName":"","VehicleID":47546},{"DriverB":"","Odometer":50364.3984375,"recvTime":1528427462000,"DeviceNo":6418483,"TrailerStatus":0,"ExStatusStr":"","Direction":273,"PointInfo":"","TrailerName":"","Driver":"","Oil":"0.0","T4":"0","OffsetLat":23.330849536415357,"DriverBPhone":"","Organ":10730,"AlarmStatus":"","RoadName":"","Lon":112.83105,"OffsetLon":112.8361707100171,"ExStatus":0,"Lat":23.333567,"SN":0,"DriverPhone":"","GPSStatus":"ACC开,定位,天线正常","T1":"0","T3":"0","GpsTime":1528428293000,"T2":"0","ExceptionStatus":"","Status":98304,"Speed":0,"RegName":"粤ABM418","SimCard":"14716418483","StatusStr":"11100010101001","linkStatus":1,"IsTrailer":0,"PlaceName":"","VehicleID":47544},{"DriverB":"","Odometer":61522.1015625,"recvTime":1528427452000,"DeviceNo":6418481,"TrailerStatus":0,"ExStatusStr":"","Direction":84,"PointInfo":"","TrailerName":"","Driver":"","Oil":"0.0","T4":"0","OffsetLat":23.159137197505945,"DriverBPhone":"","Organ":10730,"AlarmStatus":"","RoadName":"","Lon":113.47615,"OffsetLon":113.48154108093638,"ExStatus":0,"Lat":23.161734,"SN":0,"DriverPhone":"","GPSStatus":"ACC开,定位,天线正常","T1":"0","T3":"0","GpsTime":1528428283000,"T2":"0","ExceptionStatus":"","Status":98304,"Speed":0,"RegName":"粤ABM428","SimCard":"14716418481","StatusStr":"11100010101001","linkStatus":1,"IsTrailer":0,"PlaceName":"","VehicleID":48624},{"DriverB":"","Odometer":20117.19921875,"recvTime":1528427421000,"DeviceNo":6258094,"TrailerStatus":0,"ExStatusStr":"","Direction":259,"PointInfo":"","TrailerName":"","Driver":"","Oil":"0.0","T4":"0","OffsetLat":23.14004813866573,"DriverBPhone":"","Organ":10730,"AlarmStatus":"","RoadName":"","Lon":113.44785,"OffsetLon":113.4533224115319,"ExStatus":0,"Lat":23.142584,"SN":0,"DriverPhone":"","GPSStatus":"ACC开,定位,天线正常","T1":"0","T3":"0","GpsTime":1528428252000,"T2":"0","ExceptionStatus":"","Status":65536,"Speed":0,"RegName":"粤ABU445","SimCard":"14716258094","StatusStr":"11100010101001","linkStatus":1,"IsTrailer":0,"PlaceName":"","VehicleID":46615}]}
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
         * lastTime : 1528428240835
         * data : [{"DriverB":"","Odometer":19276.900390625,"recvTime":1528427442000,"DeviceNo":6258129,"TrailerStatus":0,"ExStatusStr":"","Direction":86,"PointInfo":"","TrailerName":"","Driver":"","Oil":"0.0","T4":"0","OffsetLat":23.159047290119855,"DriverBPhone":"","Organ":10730,"AlarmStatus":"","RoadName":"","Lon":113.495285,"OffsetLon":113.50061113109118,"ExStatus":0,"Lat":23.1617,"SN":0,"DriverPhone":"","GPSStatus":"ACC开,定位,天线正常","T1":"0","T3":"0","GpsTime":1528428246000,"T2":"0","ExceptionStatus":"","Status":65536,"Speed":0,"RegName":"粤ABS145","SimCard":"14716258129","StatusStr":"11100010101001","linkStatus":1,"IsTrailer":0,"PlaceName":"","VehicleID":46626},{"DriverB":"","Odometer":44041.5,"recvTime":1528427395000,"DeviceNo":6418485,"TrailerStatus":0,"ExStatusStr":"","Direction":198,"PointInfo":"","TrailerName":"","Driver":"","Oil":"0.0","T4":"0","OffsetLat":23.101777556172298,"DriverBPhone":"","Organ":10730,"AlarmStatus":"","RoadName":"","Lon":113.53495,"OffsetLon":113.54013812057802,"ExStatus":0,"Lat":23.104567,"SN":0,"DriverPhone":"","GPSStatus":"ACC开,定位,天线正常","T1":"0","T3":"0","GpsTime":1528428225000,"T2":"0","ExceptionStatus":"","Status":65536,"Speed":0,"RegName":"粤ABU146","SimCard":"14716418485","StatusStr":"11100010101001","linkStatus":1,"IsTrailer":0,"PlaceName":"","VehicleID":47546},{"DriverB":"","Odometer":50364.3984375,"recvTime":1528427462000,"DeviceNo":6418483,"TrailerStatus":0,"ExStatusStr":"","Direction":273,"PointInfo":"","TrailerName":"","Driver":"","Oil":"0.0","T4":"0","OffsetLat":23.330849536415357,"DriverBPhone":"","Organ":10730,"AlarmStatus":"","RoadName":"","Lon":112.83105,"OffsetLon":112.8361707100171,"ExStatus":0,"Lat":23.333567,"SN":0,"DriverPhone":"","GPSStatus":"ACC开,定位,天线正常","T1":"0","T3":"0","GpsTime":1528428293000,"T2":"0","ExceptionStatus":"","Status":98304,"Speed":0,"RegName":"粤ABM418","SimCard":"14716418483","StatusStr":"11100010101001","linkStatus":1,"IsTrailer":0,"PlaceName":"","VehicleID":47544},{"DriverB":"","Odometer":61522.1015625,"recvTime":1528427452000,"DeviceNo":6418481,"TrailerStatus":0,"ExStatusStr":"","Direction":84,"PointInfo":"","TrailerName":"","Driver":"","Oil":"0.0","T4":"0","OffsetLat":23.159137197505945,"DriverBPhone":"","Organ":10730,"AlarmStatus":"","RoadName":"","Lon":113.47615,"OffsetLon":113.48154108093638,"ExStatus":0,"Lat":23.161734,"SN":0,"DriverPhone":"","GPSStatus":"ACC开,定位,天线正常","T1":"0","T3":"0","GpsTime":1528428283000,"T2":"0","ExceptionStatus":"","Status":98304,"Speed":0,"RegName":"粤ABM428","SimCard":"14716418481","StatusStr":"11100010101001","linkStatus":1,"IsTrailer":0,"PlaceName":"","VehicleID":48624},{"DriverB":"","Odometer":20117.19921875,"recvTime":1528427421000,"DeviceNo":6258094,"TrailerStatus":0,"ExStatusStr":"","Direction":259,"PointInfo":"","TrailerName":"","Driver":"","Oil":"0.0","T4":"0","OffsetLat":23.14004813866573,"DriverBPhone":"","Organ":10730,"AlarmStatus":"","RoadName":"","Lon":113.44785,"OffsetLon":113.4533224115319,"ExStatus":0,"Lat":23.142584,"SN":0,"DriverPhone":"","GPSStatus":"ACC开,定位,天线正常","T1":"0","T3":"0","GpsTime":1528428252000,"T2":"0","ExceptionStatus":"","Status":65536,"Speed":0,"RegName":"粤ABU445","SimCard":"14716258094","StatusStr":"11100010101001","linkStatus":1,"IsTrailer":0,"PlaceName":"","VehicleID":46615}]
         */

        private long lastTime;
        private List<DataBean> data;

        public long getLastTime() {
            return lastTime;
        }

        public void setLastTime(long lastTime) {
            this.lastTime = lastTime;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * DriverB :
             * Odometer : 19276.900390625
             * recvTime : 1528427442000
             * DeviceNo : 6258129
             * TrailerStatus : 0
             * ExStatusStr :
             * Direction : 86
             * PointInfo :
             * TrailerName :
             * Driver :
             * Oil : 0.0
             * T4 : 0
             * OffsetLat : 23.159047290119855
             * DriverBPhone :
             * Organ : 10730
             * AlarmStatus :
             * RoadName :
             * Lon : 113.495285
             * OffsetLon : 113.50061113109118
             * ExStatus : 0
             * Lat : 23.1617
             * SN : 0
             * DriverPhone :
             * GPSStatus : ACC开,定位,天线正常
             * T1 : 0
             * T3 : 0
             * GpsTime : 1528428246000
             * T2 : 0
             * ExceptionStatus :
             * Status : 65536
             * Speed : 0
             * RegName : 粤ABS145
             * SimCard : 14716258129
             * StatusStr : 11100010101001
             * linkStatus : 1
             * IsTrailer : 0
             * PlaceName :
             * VehicleID : 46626
             */

            private String DriverB;
            private double Odometer;
            private long recvTime;
            private int DeviceNo;
            private int TrailerStatus;
            private String ExStatusStr;
            private int Direction;
            private String PointInfo;
            private String TrailerName;
            private String Driver;
            private String Oil;
            private String T4;
            private double OffsetLat;
            private String DriverBPhone;
            private int Organ;
            private String AlarmStatus;
            private String RoadName;
            private double Lon;
            private double OffsetLon;
            private int ExStatus;
            private double Lat;
            private int SN;
            private String DriverPhone;
            private String GPSStatus;
            private String T1;
            private String T3;
            private long GpsTime;
            private String T2;
            private String ExceptionStatus;
            private int Status;
            private int Speed;
            private String RegName;
            private String SimCard;
            private String StatusStr;
            private int linkStatus;
            private int IsTrailer;
            private String PlaceName;
            private int VehicleID;

            public String getDriverB() {
                return DriverB;
            }

            public void setDriverB(String DriverB) {
                this.DriverB = DriverB;
            }

            public double getOdometer() {
                return Odometer;
            }

            public void setOdometer(double Odometer) {
                this.Odometer = Odometer;
            }

            public long getRecvTime() {
                return recvTime;
            }

            public void setRecvTime(long recvTime) {
                this.recvTime = recvTime;
            }

            public int getDeviceNo() {
                return DeviceNo;
            }

            public void setDeviceNo(int DeviceNo) {
                this.DeviceNo = DeviceNo;
            }

            public int getTrailerStatus() {
                return TrailerStatus;
            }

            public void setTrailerStatus(int TrailerStatus) {
                this.TrailerStatus = TrailerStatus;
            }

            public String getExStatusStr() {
                return ExStatusStr;
            }

            public void setExStatusStr(String ExStatusStr) {
                this.ExStatusStr = ExStatusStr;
            }

            public int getDirection() {
                return Direction;
            }

            public void setDirection(int Direction) {
                this.Direction = Direction;
            }

            public String getPointInfo() {
                return PointInfo;
            }

            public void setPointInfo(String PointInfo) {
                this.PointInfo = PointInfo;
            }

            public String getTrailerName() {
                return TrailerName;
            }

            public void setTrailerName(String TrailerName) {
                this.TrailerName = TrailerName;
            }

            public String getDriver() {
                return Driver;
            }

            public void setDriver(String Driver) {
                this.Driver = Driver;
            }

            public String getOil() {
                return Oil;
            }

            public void setOil(String Oil) {
                this.Oil = Oil;
            }

            public String getT4() {
                return T4;
            }

            public void setT4(String T4) {
                this.T4 = T4;
            }

            public double getOffsetLat() {
                return OffsetLat;
            }

            public void setOffsetLat(double OffsetLat) {
                this.OffsetLat = OffsetLat;
            }

            public String getDriverBPhone() {
                return DriverBPhone;
            }

            public void setDriverBPhone(String DriverBPhone) {
                this.DriverBPhone = DriverBPhone;
            }

            public int getOrgan() {
                return Organ;
            }

            public void setOrgan(int Organ) {
                this.Organ = Organ;
            }

            public String getAlarmStatus() {
                return AlarmStatus;
            }

            public void setAlarmStatus(String AlarmStatus) {
                this.AlarmStatus = AlarmStatus;
            }

            public String getRoadName() {
                return RoadName;
            }

            public void setRoadName(String RoadName) {
                this.RoadName = RoadName;
            }

            public double getLon() {
                return Lon;
            }

            public void setLon(double Lon) {
                this.Lon = Lon;
            }

            public double getOffsetLon() {
                return OffsetLon;
            }

            public void setOffsetLon(double OffsetLon) {
                this.OffsetLon = OffsetLon;
            }

            public int getExStatus() {
                return ExStatus;
            }

            public void setExStatus(int ExStatus) {
                this.ExStatus = ExStatus;
            }

            public double getLat() {
                return Lat;
            }

            public void setLat(double Lat) {
                this.Lat = Lat;
            }

            public int getSN() {
                return SN;
            }

            public void setSN(int SN) {
                this.SN = SN;
            }

            public String getDriverPhone() {
                return DriverPhone;
            }

            public void setDriverPhone(String DriverPhone) {
                this.DriverPhone = DriverPhone;
            }

            public String getGPSStatus() {
                return GPSStatus;
            }

            public void setGPSStatus(String GPSStatus) {
                this.GPSStatus = GPSStatus;
            }

            public String getT1() {
                return T1;
            }

            public void setT1(String T1) {
                this.T1 = T1;
            }

            public String getT3() {
                return T3;
            }

            public void setT3(String T3) {
                this.T3 = T3;
            }

            public long getGpsTime() {
                return GpsTime;
            }

            public void setGpsTime(long GpsTime) {
                this.GpsTime = GpsTime;
            }

            public String getT2() {
                return T2;
            }

            public void setT2(String T2) {
                this.T2 = T2;
            }

            public String getExceptionStatus() {
                return ExceptionStatus;
            }

            public void setExceptionStatus(String ExceptionStatus) {
                this.ExceptionStatus = ExceptionStatus;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public int getSpeed() {
                return Speed;
            }

            public void setSpeed(int Speed) {
                this.Speed = Speed;
            }

            public String getRegName() {
                return RegName;
            }

            public void setRegName(String RegName) {
                this.RegName = RegName;
            }

            public String getSimCard() {
                return SimCard;
            }

            public void setSimCard(String SimCard) {
                this.SimCard = SimCard;
            }

            public String getStatusStr() {
                return StatusStr;
            }

            public void setStatusStr(String StatusStr) {
                this.StatusStr = StatusStr;
            }

            public int getLinkStatus() {
                return linkStatus;
            }

            public void setLinkStatus(int linkStatus) {
                this.linkStatus = linkStatus;
            }

            public int getIsTrailer() {
                return IsTrailer;
            }

            public void setIsTrailer(int IsTrailer) {
                this.IsTrailer = IsTrailer;
            }

            public String getPlaceName() {
                return PlaceName;
            }

            public void setPlaceName(String PlaceName) {
                this.PlaceName = PlaceName;
            }

            public int getVehicleID() {
                return VehicleID;
            }

            public void setVehicleID(int VehicleID) {
                this.VehicleID = VehicleID;
            }
        }
    }
}
