/**
 *
 */
package cn.org.bjca.zk.platform.service;

import cn.org.bjca.zk.db.entity.Cabinet;
import cn.org.bjca.zk.db.entity.CabinetDoor;
import cn.org.bjca.zk.platform.dao.CabinetDao;
import cn.org.bjca.zk.platform.dao.CabinetDoorDao;
import cn.org.bjca.zk.platform.po.CabinetDoorPO;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.page.CabinetDoorPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***************************************************************************

 * @文件名称: CabinetDoorService.java
 * @包   路   径： cn.org.bjca.zk.platform.service
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/
@Component
@Transactional(readOnly = true)
public class CabinetDoorService {

    @Autowired
    private CabinetDoorDao cabinetDoorDao;

    @Autowired
    private CabinetDao cabinetDao;

    /**
     * <p>分页查询</p>
     * @Description:
     * @param cabinetDoorPage
     * @return
     */
    public CabinetDoorPage<CabinetDoor> findPage(CabinetDoorPage<CabinetDoor> cabinetDoorPage){
        List<CabinetDoor> list =cabinetDoorDao.findPage(cabinetDoorPage);
        cabinetDoorPage.setData(list);
        return cabinetDoorPage;
    }
    //一人多柜
    public CabinetDoorPage<CabinetDoor> findPageyrdg(CabinetDoorPage<CabinetDoor> cabinetDoorPage){
        List<CabinetDoor> list =cabinetDoorDao.findPageyrdg(cabinetDoorPage);
        cabinetDoorPage.setData(list);
        return cabinetDoorPage;
    }

    /**
     * <p>根据id删除记录</p>
     * @Description:
     * @param id
     */
    @Transactional(readOnly = false)
    public int delCabinetDoorById(String id){
        return cabinetDoorDao.delCabinetDoorById(id);
    }

    /**
     * <p>保存或更新记录</p>
     * @Description:
     * @param cabinetDoor
     */
    @Transactional(readOnly = false)
    public void saveOrUpdate(CabinetDoor cabinetDoor){
        if(StringUtils.isNotBlank(cabinetDoor.getId())){//id不为空时
            cabinetDoorDao.update(cabinetDoor);
        }else{
            cabinetDoor.setId(EssPdfUtil.genrRandomUUID());
            cabinetDoorDao.save(cabinetDoor);
        }
    }

    /**
     * <p>根据ID查询对象</p>
     * @Description:
     * @param id
     * @return
     */
    public CabinetDoor findUniqueById(String id){
        return cabinetDoorDao.findUniqueById(id);
    }

    /**
     * <p>查询所有列表</p>
     * @Description:
     * @return
     */
    public List<CabinetDoor> getAll() {
        return cabinetDoorDao.getAll();
    }

    /**
     * <p>根据机柜id编号查询列表</p>
     * @Description:
     * @param cabinetNumber 机柜编号
     * @return
     */
    public List<CabinetDoor> findByCabinetNumberAndDoorNumber( String cabinetNumber,String cabinetDoorNumber) {
        CabinetDoorPO cabinetDoorPO = new CabinetDoorPO();
        cabinetDoorPO.setCabinetNumber(cabinetNumber);
        cabinetDoorPO.setCabinetDoorNumber(cabinetDoorNumber);
        return cabinetDoorDao.findByCondition(cabinetDoorPO);
    }

    /**
     * 根据员工id查看柜门
     */
    public List<CabinetDoor> selectDoorByEmployeeId(String employeeId){
        return cabinetDoorDao.selectDoorByEmployeeId(employeeId);
    }

    /**
     * 根据机柜id和柜门号查找柜门
     * @param cabinetId
     * @param cabinetDoorNumber
     * @return
     */
    public CabinetDoor selectDoorByCabinetIdAndCabinetDoorNumber(String cabinetId, String cabinetDoorNumber){
        CabinetDoor cabinetDoor = cabinetDoorDao.selectDoorByCabinetIdAndCabinetDoorNumber(cabinetId,cabinetDoorNumber);
        return cabinetDoor;
    }

    public List<CabinetDoor> findByCabinetID(String cabinetID){
        return cabinetDoorDao.findByCabinetID(cabinetID);
    }

    public CabinetDoor selectDoorByEmployeeIdAndIP(String employeeId, String ip){
        Cabinet cabinet = cabinetDao.findByIP(ip);
        if(cabinet!=null){
            return cabinetDoorDao.findByCabinetIDAndEmployeeID(cabinet.getId(), employeeId);
        }return null;
    }

    public List<String> doorNumberToDoorName(List<CabinetDoor> list1,List<CabinetDoor> list2,String flood){
        List<String> listS = new ArrayList<>();
        if(flood.equals("1")){
            for (CabinetDoor cabinetDoor:list2){
                cabinetDoor.setCabinetDoorNumber(Integer.parseInt(cabinetDoor.getCabinetDoorNumber())+119+"");
            }
        }else if(flood.equals("2")){
            for (CabinetDoor cabinetDoor:list1){
                cabinetDoor.setCabinetDoorNumber(Integer.parseInt(cabinetDoor.getCabinetDoorNumber())+119+"");
            }
        }
        list1.addAll(list2);
        if(flood.equals("1")||flood.equals("2")){
            for(CabinetDoor cabinetDoor:list1){
                int cabinetDoorNumber = Integer.parseInt(cabinetDoor.getCabinetDoorNumber());
                int q = cabinetDoorNumber/17;
                int l = cabinetDoorNumber%17+1;
                String qu = "";
                if(q==0){
                    qu="A";
                }else if(q==1){
                    qu="B";
                }else if(q==2){
                    qu="C";
                }else if(q==3){
                    qu="D";
                }else if(q==4){
                    qu="E";
                }else if(q==5){
                    qu="F";
                }else if(q==6){
                    qu="G";
                }else if(q==7){
                    qu="H";
                }else if(q==8){
                    qu="I";
                }else if(q==9){
                    qu="J";
                }else if(q==10){
                    qu="K";
                }else if(q==11){
                    qu="L";
                }else if(q==12){
                    qu="M";
                }else if(q==13){
                    qu="N";
                }
                listS.add(qu+l);
            }
        }
        return listS;
    }

    public List<Map<String,String>> doorNumberToDoorName(String ip,List<Integer> list){
        List<Map<String,String>> maps = new ArrayList<>();
        int type = 0;
        if(ip.equals("10.11.28.21")||ip.equals("10.11.28.23")||ip.equals("10.11.28.25")) {
            type = 1;
        }else if(ip.equals("10.11.28.22")||ip.equals("10.11.28.24")||ip.equals("10.11.28.26")) {
            type = 1;
        }else if(ip.equals("10.11.28.20")){//20层
            type = 2;
        }else if(ip.equals("10.11.28.27")){//12层
            type = 3;
        }else if(ip.equals("10.17.36.86")){
            type = 4;
        }else{
            type = 2;
        }
        if(type==1){
            for(int i:list){
                int q = (i-1)/17;
                int l = (i%17);
                if(l==0){
                    l=17;
                }
                String qu = "";
                if(q==0){
                    qu="A";
                }else if(q==1){
                    qu="B";
                }else if(q==2){
                    qu="C";
                }else if(q==3){
                    qu="D";
                }else if(q==4){
                    qu="E";
                }else if(q==5){
                    qu="F";
                }else if(q==6){
                    qu="G";
                }else if(q==7){
                    qu="H";
                }else if(q==8){
                    qu="I";
                }else if(q==9){
                    qu="J";
                }else if(q==10){
                    qu="K";
                }else if(q==11){
                    qu="L";
                }else if(q==12){
                    qu="M";
                }else if(q==13){
                    qu="N";
                }
                int key = q*17+l;
                if(key>119){
                    key = key-119;
                }
                Map<String,String> map = new HashMap();
                map.put(key+"",qu+l);
                maps.add(map);
            }
        }else if(type==2){
            for(int i:list){
                int q = (i-1)/10;
                int l = (i%10);
                if(l==0){
                    l=10;
                }
                String qu = "";
                if(q==0){
                    qu="A";
                }else if(q==1){
                    qu="B";
                }else if(q==2){
                    qu="C";
                }else if(q==3){
                    qu="D";
                }else if(q==4){
                    qu="E";
                }else if(q==5){
                    qu="F";
                }
                int key = q*10+l;
                Map<String,String> map = new HashMap();
                map.put(key+"",qu+l);
                maps.add(map);
            }
        }else if(type==3) {
            for (int i : list) {
                String qu = "";
                Map<String, String> map = new HashMap();
                int key = i;
                if (i <= 13) {
                    qu = "A" + i;
                } else if (i > 21) {
                    qu = "D" + (i - 21);
                } else {
                    switch (i) {
                        case 14:
                            qu = "B1";
                            break;
                        case 15:
                            qu = "B2";
                            break;
                        case 16:
                            qu = "B3";
                            break;
                        case 17:
                            qu = "B4";
                            break;
                        case 18:
                            qu = "C1";
                            break;
                        case 19:
                            qu = "C2";
                            break;
                        case 20:
                            qu = "C3";
                            break;
                        case 21:
                            qu = "C4";
                            break;
                    }
                }
                map.put(key + "", qu);
                maps.add(map);
            }
        }else if(type==4){
            for (int i : list) {
                String qu = "";
                Map<String, String> map = new HashMap();
                int key = i;
                if (i <= 15) {
                    qu = "A" + i;
                } else if (i > 25) {
                    qu = "D" + (i - 25);
                } else {
                    switch (i) {
                        case 16:
                            qu = "B1";
                            break;
                        case 17:
                            qu = "B2";
                            break;
                        case 18:
                            qu = "B3";
                            break;
                        case 19:
                            qu = "B4";
                            break;
                        case 20:
                            qu = "B5";
                            break;
                        case 21:
                            qu = "C1";
                            break;
                        case 22:
                            qu = "C2";
                            break;
                        case 23:
                            qu = "C3";
                            break;
                        case 24:
                            qu = "C4";
                            break;
                        case 25:
                            qu = "C5";
                            break;
                    }
                }
                map.put(key + "", qu);
                maps.add(map);
            }
        }
        return maps;
    }

    public String setCabinetDoorName(CabinetDoor cabinetDoor){
        String cabinetId = cabinetDoor.getCabinetId();
        Cabinet cabinet = cabinetDao.findUniqueById(cabinetId);
        String ip = cabinet.getCabinetIP();
        int number = 0;
        int type = 0;
        if(ip.equals("10.11.28.21")||ip.equals("10.11.28.23")||ip.equals("10.11.28.25")) {
            number = Integer.parseInt(cabinetDoor.getCabinetDoorNumber());
            type = 1;
        }else if(ip.equals("10.11.28.22")||ip.equals("10.11.28.24")||ip.equals("10.11.28.26")) {
            number = Integer.parseInt(cabinetDoor.getCabinetDoorNumber()) + 119;
            type = 1;
        }else if(ip.equals("10.11.28.20")){//20层
            type = 2;
            number = Integer.parseInt(cabinetDoor.getCabinetDoorNumber());
        }else if(ip.equals("10.11.28.27")){//12层
            type = 3;
            number = Integer.parseInt(cabinetDoor.getCabinetDoorNumber());
        }else if(ip.equals("10.17.36.86")){//上海
            type = 4;
            number = Integer.parseInt(cabinetDoor.getCabinetDoorNumber());
        }
        if(type==1){
            int q = number/17;
            int l = (number%17);
            String qu = "";
            if(q==0){
                qu="A";
            }else if(q==1){
                qu="B";
            }else if(q==2){
                qu="C";
            }else if(q==3){
                qu="D";
            }else if(q==4){
                qu="E";
            }else if(q==5){
                qu="F";
            }else if(q==6){
                qu="G";
            }else if(q==7){
                qu="H";
            }else if(q==8){
                qu="I";
            }else if(q==9){
                qu="J";
            }else if(q==10){
                qu="K";
            }else if(q==11){
                qu="L";
            }else if(q==12){
                qu="M";
            }else if(q==13){
                qu="N";
            }
            return qu+l;
        }else if(type==2){
            int q = number/10;
            int l = (number%10);
            if(l==0){
                l=10;
            }
            String qu = "";
            if(q==0){
                qu="A";
            }else if(q==1){
                qu="B";
            }else if(q==2){
                qu="C";
            }else if(q==3){
                qu="D";
            }else if(q==4){
                qu="E";
            }else if(q==5){
                qu="F";
            }
            return qu+l;
        }else if(type==3){
            if(number<=13){
                return "A"+number;
            }else if(number>21){
                return "D"+(number-21);
            }else{
                switch (number){
                    case 14:
                        return "B1";
                    case 15:
                        return "B2";
                    case 16:
                        return "B3";
                    case 17:
                        return "B4";
                    case 18:
                        return "C1";
                    case 19:
                        return "C2";
                    case 20:
                        return "C3";
                    case 21:
                        return "C4";
                }
            }
        }else if(type==4){
            if(number<=15){
                return "A"+number;
            }else if(number>25){
                return "D"+(number-25);
            }else{
                switch (number){
                    case 16:
                        return "B1";
                    case 17:
                        return "B2";
                    case 18:
                        return "B3";
                    case 19:
                        return "B4";
                    case 20:
                        return "B5";
                    case 21:
                        return "C1";
                    case 22:
                        return "C2";
                    case 23:
                        return "C3";
                    case 24:
                        return "C4";
                    case 25:
                        return "C5";
                }
            }
        }
        return "";

    }



}
