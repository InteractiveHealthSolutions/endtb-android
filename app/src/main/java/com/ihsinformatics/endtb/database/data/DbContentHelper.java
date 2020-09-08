package com.ihsinformatics.endtb.database.data;


import com.ihsinformatics.endtb.database.Entities.Address;
import com.ihsinformatics.endtb.database.Entities.AddressDao;
import com.ihsinformatics.endtb.database.Entities.Concept;
import com.ihsinformatics.endtb.database.Entities.ConceptDao;
import com.ihsinformatics.endtb.database.Entities.DaoMaster;
import com.ihsinformatics.endtb.database.Entities.Drug;
import com.ihsinformatics.endtb.database.Entities.DrugDao;
import com.ihsinformatics.endtb.database.Entities.DrugOrders;
import com.ihsinformatics.endtb.database.Entities.DrugOrdersDao;
import com.ihsinformatics.endtb.database.Entities.Encounter;
import com.ihsinformatics.endtb.database.Entities.EncounterDao;
import com.ihsinformatics.endtb.database.Entities.EncounterType;
import com.ihsinformatics.endtb.database.Entities.EncounterTypeDao;
import com.ihsinformatics.endtb.database.Entities.IdentifierType;
import com.ihsinformatics.endtb.database.Entities.IdentifierTypeDao;
import com.ihsinformatics.endtb.database.Entities.Location;
import com.ihsinformatics.endtb.database.Entities.LocationAttribute;
import com.ihsinformatics.endtb.database.Entities.LocationAttributeDao;
import com.ihsinformatics.endtb.database.Entities.LocationAttributeType;
import com.ihsinformatics.endtb.database.Entities.LocationAttributeTypeDao;
import com.ihsinformatics.endtb.database.Entities.LocationDao;
import com.ihsinformatics.endtb.database.Entities.LocationTag;
import com.ihsinformatics.endtb.database.Entities.LocationTagDao;
import com.ihsinformatics.endtb.database.Entities.LocationTagMap;
import com.ihsinformatics.endtb.database.Entities.LocationTagMapDao;
import com.ihsinformatics.endtb.database.Entities.Obs;
import com.ihsinformatics.endtb.database.Entities.ObsDao;
import com.ihsinformatics.endtb.database.Entities.Order;
import com.ihsinformatics.endtb.database.Entities.OrderDao;
import com.ihsinformatics.endtb.database.Entities.OrderType;
import com.ihsinformatics.endtb.database.Entities.OrderTypeDao;
import com.ihsinformatics.endtb.database.Entities.Patient;
import com.ihsinformatics.endtb.database.Entities.PatientAttributes;
import com.ihsinformatics.endtb.database.Entities.PatientAttributesDao;
import com.ihsinformatics.endtb.database.Entities.PatientDao;
import com.ihsinformatics.endtb.database.Entities.PatientIdentifier;
import com.ihsinformatics.endtb.database.Entities.PatientIdentifierDao;
import com.ihsinformatics.endtb.database.Entities.Permission;
import com.ihsinformatics.endtb.database.Entities.PermissionDao;
import com.ihsinformatics.endtb.database.Entities.PersonAttributeType;
import com.ihsinformatics.endtb.database.Entities.PersonAttributeTypeDao;
import com.ihsinformatics.endtb.database.Entities.Role;
import com.ihsinformatics.endtb.database.Entities.RoleDao;
import com.ihsinformatics.endtb.database.Entities.SendableData;
import com.ihsinformatics.endtb.database.Entities.SendableDataDao;
import com.ihsinformatics.endtb.database.Entities.User;
import com.ihsinformatics.endtb.database.Entities.UserDao;
import com.ihsinformatics.endtb.database.Entities.UserPermissions;
import com.ihsinformatics.endtb.database.Entities.UserPermissionsDao;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Naveed Iqbal on 10/29/2017.
 * Email: h.naveediqbal@gmail.com
 */
public class DbContentHelper {
    private static DbContentHelper ourInstance = new DbContentHelper();

    public static synchronized DbContentHelper getInstance() {
        return ourInstance;
    }

    private DbContentHelper() {
    }
    // TODO use generic method
    public PersonAttributeType fetchPersonAttributeType(String typeName) {
        PersonAttributeType personAttributeType = null;
        PersonAttributeTypeDao personAttributeTypeDao = ELimsApplication.daoSession.getPersonAttributeTypeDao();
        List<PersonAttributeType> personAttributeTypeList = personAttributeTypeDao.queryBuilder()
                .where(PersonAttributeTypeDao.Properties.AttributeName.like(typeName))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(personAttributeTypeList.size()>0)
            personAttributeType = personAttributeTypeList.get(0);

        return personAttributeType;
    }

    public PersonAttributeType fetchPersonAttributeTypeByUUID(String uuid) {
        PersonAttributeType personAttributeType = null;
        PersonAttributeTypeDao personAttributeTypeDao = ELimsApplication.daoSession.getPersonAttributeTypeDao();
        List<PersonAttributeType> personAttributeTypeList = personAttributeTypeDao.queryBuilder()
                .where(PersonAttributeTypeDao.Properties.Uuid.like(uuid))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(personAttributeTypeList.size()>0)
            personAttributeType = personAttributeTypeList.get(0);

        return personAttributeType;
    }

    public Patient fetchPatientByUUID(String id) {
        Patient patient = null;
        PatientDao patientDao = ELimsApplication.daoSession.getPatientDao();
        List<Patient> patientList = patientDao.queryBuilder()
                .where(PatientDao.Properties.Uuid.eq(id))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(patientList.size()>0)
            patient = patientList.get(0);

        return patient;
    }

    public Patient fetchPatientById(Long id) {
        Patient patient = null;
        PatientDao patientDao = ELimsApplication.daoSession.getPatientDao();
        List<Patient> patientList = patientDao.queryBuilder()
                .where(PatientDao.Properties.PatientId.eq(id))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(patientList.size()>0)
            patient = patientList.get(0);

        return patient;
    }

    public List<Patient> fetchPatientsByAttribute(String attributeType, String value) {

        PatientAttributesDao attributesDao = ELimsApplication.daoSession.getPatientAttributesDao();
        List<PatientAttributes> patientAttributes = fetchPatientAttributesByValue(attributeType, value);
        List<Long> patientIds = new ArrayList<>();
        for(PatientAttributes pa: patientAttributes) {
            patientIds.add(pa.getPatientId());
        }

        PatientDao patientDao = ELimsApplication.daoSession.getPatientDao();
        List<Patient> patientList = patientDao.queryBuilder()
                .where(PatientDao.Properties.PatientId.in(patientIds))
                /*.orderAsc(Properties.LastName)*/
                .list();

        if(patientList == null) patientList = new ArrayList<>();
        return patientList;
    }

    public List<Patient> fetchAllPatients() {
        Patient patient = null;
        PatientDao patientDao = ELimsApplication.daoSession.getPatientDao();
        List<Patient> patientList = patientDao.queryBuilder().list();

        return patientList;
    }

    public Role fetchRoleByName(String roleNme) {
        Role role = null;
        RoleDao roleDao = ELimsApplication.daoSession.getRoleDao();
        List<Role> conceptList = roleDao.queryBuilder()
                .where(RoleDao.Properties.RoleName.like(roleNme))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(conceptList.size()>0)
            role = conceptList.get(0);

        return role;
    }

    public EncounterType fetchEncounterType(String typeName) {
        EncounterType encounterType = null;
        EncounterTypeDao encounterTypeDao = ELimsApplication.daoSession.getEncounterTypeDao();
        List<EncounterType> encounterTypeList = encounterTypeDao.queryBuilder()
                .where(EncounterTypeDao.Properties.TypeName.like(typeName))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(encounterTypeList.size()>0)
            encounterType = encounterTypeList.get(0);

        return encounterType;
    }

    public EncounterType fetchEncounterTypeByUUID(String uuid) {
        EncounterType encounterType = null;
        EncounterTypeDao encounterTypeDao = ELimsApplication.daoSession.getEncounterTypeDao();
        List<EncounterType> encounterTypeList = encounterTypeDao.queryBuilder()
                .where(EncounterTypeDao.Properties.Uuid.like(uuid))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(encounterTypeList.size()>0)
            encounterType = encounterTypeList.get(0);

        return encounterType;
    }

    public OrderType fetchOrderType(String typeName) {
        OrderType orderType = null;
        OrderTypeDao encounterTypeDao = ELimsApplication.daoSession.getOrderTypeDao();
        List<OrderType> orderTypeList = encounterTypeDao.queryBuilder()
                .where(OrderTypeDao.Properties.Name.like(typeName))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(orderTypeList.size()>0)
            orderType = orderTypeList.get(0);

        return orderType;
    }

    public IdentifierType fetchIdentifierType(String typeName) {
        IdentifierType identifierType = null;
        IdentifierTypeDao identifierTypeDao = ELimsApplication.daoSession.getIdentifierTypeDao();
        List<IdentifierType> identifierTypeList = identifierTypeDao.queryBuilder()
                .where(IdentifierTypeDao.Properties.IdentifierTypeName.like(typeName))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(identifierTypeList.size()>0)
            identifierType = identifierTypeList.get(0);

        return identifierType;
    }

    public Concept fetchConcept(Long conceptId) {
        Concept concept = null;
        ConceptDao conceptDao = ELimsApplication.daoSession.getConceptDao();
        List<Concept> conceptList = conceptDao.queryBuilder()
                .where(ConceptDao.Properties.Id.eq(conceptId))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(conceptList.size()>0)
            concept = conceptList.get(0);

        return concept;
    }

    public Concept fetchConcept(String conceptShortName) {
        Concept concept = null;
        ConceptDao conceptDao = ELimsApplication.daoSession.getConceptDao();
        List<Concept> conceptList = conceptDao.queryBuilder()
                .where(ConceptDao.Properties.ShortName.like(conceptShortName))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(conceptList.size()>0)
            concept = conceptList.get(0);

        return concept;
    }

    public Concept fetchConceptByUUID(String uuid) {
        Concept concept = null;
        ConceptDao conceptDao = ELimsApplication.daoSession.getConceptDao();
        List<Concept> conceptList = conceptDao.queryBuilder()
                .where(ConceptDao.Properties.Uuid.like(uuid))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(conceptList.size()>0)
            concept = conceptList.get(0);

        return concept;
    }

    public List<Encounter> fetchEncountersByType(String encounterTypeName) {
        EncounterType encounterType = fetchEncounterType(encounterTypeName);
        EncounterDao encounterDao = ELimsApplication.daoSession.getEncounterDao();
        List<Encounter> encounterList = encounterDao.queryBuilder()
                .where(EncounterDao.Properties.EncounterTypeId.eq(encounterType.getId()))
                /*.orderAsc(Properties.LastName)*/
                .list();
        return encounterList;
    }

    public Encounter fetchEncounterById(Long encounterId) {
        Encounter encounter = null;
        EncounterDao encounterDao = ELimsApplication.daoSession.getEncounterDao();
        List<Encounter> encounterList = encounterDao.queryBuilder()
                .where(EncounterDao.Properties.Id.eq(encounterId))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(encounterList.size()>0)
            encounter = encounterList.get(0);

        return encounter;
    }

    public Encounter fetchEncounterByUUID(String uuid) {
        Encounter encounter = null;
        EncounterDao encounterDao = ELimsApplication.daoSession.getEncounterDao();
        List<Encounter> encounterList = encounterDao.queryBuilder()
                .where(EncounterDao.Properties.Uuid.eq(uuid))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(encounterList.size()>0)
            encounter = encounterList.get(0);

        return encounter;
    }

    public List<Obs> fetchAdministeredDrugDoses(long date1, long date2, Long drugConceptId, Long patientId) {
        ObsDao encounterDao = ELimsApplication.daoSession.getObsDao();
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        List<Obs> encounterList = ELimsApplication.daoSession.getObsDao().queryRaw(
        " inner join " + EncounterDao.TABLENAME + " e "
                + " on T."+ObsDao.Properties.EncounterId.columnName+" = e." + EncounterDao.Properties.Id.columnName
                + " where T." + ObsDao.Properties.ConceptId.columnName
                + " = ? "
                + "and "+EncounterDao.Properties.EncounterDate.columnName+" between "+date1+" and "+date2+" "
                +"and "+EncounterDao.Properties.PatientId.columnName+"="+patientId, String.valueOf(drugConceptId));

        return encounterList;
    }

    public List<Obs> fetchObsByParentConcept(Long obsConceptId, String... parentObsLocalUUID) {
        ObsDao encounterDao = ELimsApplication.daoSession.getObsDao();
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        List<Obs> encounterList = ELimsApplication.daoSession.getObsDao().queryBuilder()
                .where(ObsDao.Properties.ParentObsLocalUUID.in(parentObsLocalUUID), ObsDao.Properties.ConceptId.eq(obsConceptId))
                .list();;

        return encounterList;
    }

    public Order fetchOrderById(Long orderId) {
        Order encounter = null;
        OrderDao orderDao = ELimsApplication.daoSession.getOrderDao();
        List<Order> encounterList = orderDao.queryBuilder()
                .where(OrderDao.Properties.Id.eq(orderId))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(encounterList.size()>0)
            encounter = encounterList.get(0);

        return encounter;
    }

    public Order fetchOrderByAccessionNumber(String accessionNumber) {
        Order encounter = null;
        OrderDao orderDao = ELimsApplication.daoSession.getOrderDao();
        List<Order> encounterList = orderDao.queryBuilder()
                .where(OrderDao.Properties.AccessionNumber.eq(accessionNumber))
                /*.orderAsc(Properties.LastName)*/
                .list();
        if(encounterList.size()>0)
            encounter = encounterList.get(0);

        return encounter;
    }

    public Encounter fetchPatientEncounter(Long patientDBId, String encounterTypeName) {
        EncounterType encounterType = fetchEncounterType(encounterTypeName);
        EncounterDao encounterDao = ELimsApplication.daoSession.getEncounterDao();
        List<Encounter> encounterList = encounterDao.queryBuilder()
                .where(EncounterDao.Properties.EncounterTypeId.eq(encounterType.getId()), EncounterDao.Properties.PatientId.eq(patientDBId))
                /*.orderAsc(Properties.LastName)*/
                .list();

        if (encounterList.size()>0)
            return encounterList.get(0);

        return null;
    }

    public Encounter fetchPatientEncounterByUUID(Long patientDBId, String uuid) {
        EncounterType encounterType = fetchEncounterType(uuid);
        EncounterDao encounterDao = ELimsApplication.daoSession.getEncounterDao();
        List<Encounter> encounterList = encounterDao.queryBuilder()
                .where(EncounterDao.Properties.EncounterTypeId.eq(encounterType.getId()), EncounterDao.Properties.PatientId.eq(patientDBId))
                /*.orderAsc(Properties.LastName)*/
                .list();

        if (encounterList.size()>0)
            return encounterList.get(0);

        return null;
    }

    public List<Encounter> fetchAllEncounters() {
        EncounterDao encounterDao = ELimsApplication.daoSession.getEncounterDao();
        List<Encounter> encounterList = encounterDao.queryBuilder()
                /*.orderAsc(Properties.LastName)*/
                .list();
        return encounterList;
    }

    public List<SendableData> fetchAllSendableDataByType(String dataType) {
        SendableDataDao obsDao = ELimsApplication.daoSession.getSendableDataDao();
        // String[] columns = obsDao.getAllColumns();
        List<SendableData> sendableDataList = obsDao.queryBuilder()
                .where(SendableDataDao.Properties.Dataype.eq(dataType))
            /*.orderAsc(Properties.LastName)*/
                .list();
        return sendableDataList;
    }

    public Address fetchAddress(Long patientId) {
        AddressDao obsDao = ELimsApplication.daoSession.getAddressDao();
        List<Address> obsList = obsDao.queryBuilder()
                .where(AddressDao.Properties.PatientId.eq(patientId))
            /*.orderAsc(Properties.LastName)*/
                .list();
        if(obsList.size()>0)
            return obsList.get(0);

        return null;
    }

    public List<Order> fetchOrders(Long patientId) {
        OrderDao obsDao = ELimsApplication.daoSession.getOrderDao();
        String[] columns = obsDao.getAllColumns();
        List<Order> obsList = obsDao.queryBuilder()
                .where(OrderDao.Properties.PatientId.eq(patientId))
            /*.orderAsc(Properties.LastName)*/
                .list();
        return obsList;
    }

    public List<Order> fetchAllOrders(Boolean voided) {
        OrderDao obsDao = ELimsApplication.daoSession.getOrderDao();
        List<Order> obsList = obsDao.queryBuilder()
                .where( OrderDao.Properties.Voided.eq(voided))
                .list();
        return obsList;
    }

    public List<Obs> fetchEncounterObs(Long encounterId, Boolean voided) {
        ObsDao obsDao = ELimsApplication.daoSession.getObsDao();
        List<Obs> obsList = obsDao.queryBuilder()
                .where(ObsDao.Properties.EncounterId.eq(encounterId), ObsDao.Properties.Voided.eq(voided))
            /*.orderAsc(Properties.LastName)*/
                .list();
        return obsList;
    }

    public Obs fetchEncounterObsByConcept(Long encounterId, Long conceptId, Boolean voided) {
        ObsDao obsDao = ELimsApplication.daoSession.getObsDao();
        List<Obs> obsList = obsDao.queryBuilder()
                .where(ObsDao.Properties.EncounterId.eq(encounterId), ObsDao.Properties.ConceptId.eq(conceptId), ObsDao.Properties.Voided.eq(voided))
            /*.orderAsc(Properties.LastName)*/
                .list();

        if (obsList.size()>0)
            return obsList.get(0);
        return null;
    }

    public Obs fetchObsByConceptUUID(Long encounterId, String uuid) {
        Concept concept = fetchConceptByUUID(uuid);
        ObsDao obsDao = ELimsApplication.daoSession.getObsDao();
        List<Obs> obsList = obsDao.queryBuilder()
                .where(ObsDao.Properties.EncounterId.eq(encounterId), ObsDao.Properties.ConceptId.eq(concept.getId()))
            /*.orderAsc(Properties.LastName)*/
                .list();

        if (obsList.size()>0)
            return obsList.get(0);
        return null;
    }

    public Obs fetchObs(Long encounterId, String conceptName) {
        Concept concept = fetchConcept(conceptName);
        ObsDao obsDao = ELimsApplication.daoSession.getObsDao();
        List<Obs> obsList = obsDao.queryBuilder()
                .where(ObsDao.Properties.EncounterId.eq(encounterId), ObsDao.Properties.ConceptId.eq(concept.getId()))
            /*.orderAsc(Properties.LastName)*/
                .list();

        if (obsList.size()>0)
            return obsList.get(0);
        return null;
    }

    public List<Patient> searchPatient(String division, String district, String upazila, String union, String name, String age, String gender, String occupation) {
        List<Patient> patientList = new ArrayList<>();
        /*PatientDao patientDao = ELimsApplication.daoSession.getPatientDao();
        QueryBuilder<Patient> queryBuilder;

            queryBuilder = patientDao.queryBuilder()
                    .where(
                            PatientDao.Properties.GivenName.like(name.equals("")?"%":("%"+name+"%")),
                            PatientDao.Properties.Gender.like(gender.equals("")?"%":gender),
                            PatientDao.Properties.Age.like(age.equals("")?"%":age));

        Join addressJoin = queryBuilder.join(Address.class, AddressDao.Properties.PatientId);
        addressJoin.where(
                AddressDao.Properties.Division.like(division.equals("")?"%":division),
                AddressDao.Properties.District.like(district.equals("")?"%":district),
                AddressDao.Properties.Upazilla.like(upazila.equals("")?"%":upazila),
                AddressDao.Properties.Union.like(union.equals("")?"%":union));
        if(!occupation.equals("")) {
            Join patientAttributeJoin = queryBuilder.join(PatientAttributes.class, PatientAttributesDao.Properties.PatientId);
            patientAttributeJoin.where(PatientAttributesDao.Properties.Value.like(occupation));
        }
        patientList = queryBuilder.list();*/
        return patientList;
    }

    public List<Patient> searchPatientForTest(Long[] lab, Long encounterType, String age, String occupation) {
        List<Patient> patientList = new ArrayList<>();
        PatientDao patientDao = ELimsApplication.daoSession.getPatientDao();
        QueryBuilder<Patient> queryBuilder;

        queryBuilder = patientDao.queryBuilder().where(PatientDao.Properties.Age.like(age.equals("")?"%":age));

        Join patientAttributeJoin = queryBuilder.join(PatientAttributes.class, PatientAttributesDao.Properties.PatientId);
        patientAttributeJoin.where(PatientAttributesDao.Properties.Value.like(occupation.equals("")?"%":occupation));

        Join encounterJoin = queryBuilder.join(Encounter.class, EncounterDao.Properties.PatientId);
        encounterJoin.where(
                        EncounterDao.Properties.LocationId.in(lab),
                        EncounterDao.Properties.EncounterTypeId.eq(encounterType));

        patientList = queryBuilder.list();

        return patientList;
    }

    public PatientAttributes fetchPatientAttribute(Long patientId, String attributeType) {
        Long attributeTypeId = fetchPersonAttributeType(attributeType).getAttributeId();
        List<PatientAttributes> patientAttributesList = ELimsApplication.daoSession
                .getPatientAttributesDao()
                .queryBuilder()
                .where(PatientAttributesDao.Properties.PatientId.eq(patientId), PatientAttributesDao.Properties.AttributeId.eq(attributeTypeId))
                .list();
        if (patientAttributesList.size()>0)
            return patientAttributesList.get(0);
        else
            return null;
    }

    public List<PatientAttributes> fetchPatientAttributesByValue(String attributeType, String value) {
        Long attributeTypeId = fetchPersonAttributeType(attributeType).getAttributeId();
        List<PatientAttributes> patientAttributesList = ELimsApplication.daoSession
                .getPatientAttributesDao()
                .queryBuilder()
                .where(PatientAttributesDao.Properties.AttributeId.eq(attributeTypeId), PatientAttributesDao.Properties.Value.eq(value))
                .list();

        return patientAttributesList;
    }

    public PatientIdentifier fetchPatientIdentifier(String patientIdentifier, String identifierType) {
        Long identifierTypeId = fetchIdentifierType(identifierType).getId();
        List<PatientIdentifier> patientAttributesList = ELimsApplication.daoSession
                .getPatientIdentifierDao()
                .queryBuilder()
                .where(PatientIdentifierDao.Properties.Identifier.eq(patientIdentifier), PatientIdentifierDao.Properties.IdentifierTypeName.eq(identifierTypeId))
                .list();
        if (patientAttributesList.size()>0)
            return patientAttributesList.get(0);
        else
            return null;
    }

    public PatientIdentifier fetchPatientIdentifier(Long patientId, String identifierType) {
        Long identifierTypeId = fetchIdentifierType(identifierType).getId();
        List<PatientIdentifier> patientAttributesList = ELimsApplication.daoSession
                .getPatientIdentifierDao()
                .queryBuilder()
                .where(PatientIdentifierDao.Properties.PatientId.eq(patientId), PatientIdentifierDao.Properties.IdentifierTypeName.eq(identifierTypeId))
                .list();
        if (patientAttributesList.size()>0)
            return patientAttributesList.get(0);
        else
            return null;
    }

    public Location fetchLocationByUUID(String uuid) {
        if(uuid==null)
            return null;
        LocationDao locationDao = ELimsApplication.daoSession.getLocationDao();
        List<Location> locationsList = locationDao.queryBuilder()
                .where(LocationDao.Properties.Uuid.eq(uuid))
                .list();
        if (locationsList.size()>0)
            return locationsList.get(0);
        else
            return null;
    }

    public Location fetchLocationById(Long id) {
        if(id==null)
            return null;
        LocationDao locationDao = ELimsApplication.daoSession.getLocationDao();
        List<Location> locationsList = locationDao.queryBuilder()
                .where(LocationDao.Properties.Id.eq(id))
                .list();
        if (locationsList.size()>0)
            return locationsList.get(0);
        else
            return null;
    }

    public Location fetchLocationByName(String name) {
        if(name==null)
            return null;
        LocationDao locationDao = ELimsApplication.daoSession.getLocationDao();
        List<Location> locationsList = locationDao.queryBuilder()
                .where(LocationDao.Properties.Name.eq(name))
                .list();
        if (locationsList.size()>0)
            return locationsList.get(0);
        else
            return null;
    }

    public void insertLocation(Location location) {

        LocationDao locationDao = ELimsApplication.daoSession.getLocationDao();
        LocationAttributeDao locationAttributeDao = ELimsApplication.daoSession.getLocationAttributeDao();
        LocationTagMapDao locationTagMapDao = ELimsApplication.daoSession.getLocationTagMapDao();
        Location parent = fetchLocationByUUID(location.getParentLocationUUID());
        if(parent!=null)
            location.setParentLocation(parent.getId());
        Long locationId = locationDao.insertOrReplace(location);
        ArrayList<LocationTag> locationTagsList = location.getLocationTags();
        if (locationTagsList!=null) {
            ArrayList<LocationTagMap> locationTagMaps = new ArrayList<>();
            for (LocationTag locationTag : locationTagsList) {
                LocationTagMap locationTagMap = new LocationTagMap(null, locationId, locationTag.getId());
                locationTagMaps.add(locationTagMap);
            }
            locationTagMapDao.insertOrReplaceInTx(locationTagMaps);
        }
        ArrayList<LocationAttribute> locationAttributesList = location.getLocationAttributes();
        if(locationAttributesList!=null) {
            for(LocationAttribute la: locationAttributesList) {
                la.setLocationId(locationId);
            }
            locationAttributeDao.insertOrReplaceInTx(locationAttributesList);
        }
    }

    public synchronized void insertLocations(List<Location> sortedLocations) {
        Database db = ELimsApplication.daoSession.getDatabase();
        db.beginTransaction();
        for(int i = 0; i<sortedLocations.size(); i++) {
            Location location = sortedLocations.get(i);
            Location savedLocation = DbContentHelper.getInstance().fetchLocationByUUID(location.getUuid());
            if(savedLocation != null)
                continue;

            insertLocation(location);
            System.out.println("Added: " + i);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public synchronized void insertOrUpdateUsers(List<User> users) {
        UserDao userDao = ELimsApplication.daoSession.getUserDao();
        for (int i=0; i<users.size(); i++) {
            User user = DbContentHelper.getInstance().fetchUserByUUID(users.get(i).getUuid());
            if (user == null) {
                userDao.insert(user);
            } else {
                userDao.update(user);
            }
        }
    }

    public List<Location> fetchLocationsByTag(String tag, String parentLocationName) throws IllegalStateException {
        Location parent = fetchLocationByName(parentLocationName);
        List<Location> locationList = new ArrayList<>();
        LocationTag locationTag = fetchLocationTagByName(tag);
        if(locationTag == null)
            throw new IllegalStateException("No location tag found against location tag "+tag);
        // QueryBuilder.LOG_SQL = true;

        LocationDao locationDao = ELimsApplication.daoSession.getLocationDao();
        QueryBuilder<Location> queryBuilder = locationDao.queryBuilder();
        if(parent!=null)
            queryBuilder.where(LocationDao.Properties.ParentLocation.eq(parent.getId()));

        Join locationTagMapJoin = queryBuilder.join(LocationTagMap.class, LocationTagMapDao.Properties.LocationId);
        locationTagMapJoin.where(LocationTagMapDao.Properties.LocationTagId.eq(locationTag.getId()));

        locationList = queryBuilder.list();
        return locationList;
    }

    public LocationTag fetchLocationTagByName(String tagName) {
        LocationTagDao locationTagDao = ELimsApplication.daoSession.getLocationTagDao();
        List<LocationTag> tagList = locationTagDao.queryBuilder()
                .where(LocationTagDao.Properties.Name.eq(tagName))
            /*.orderAsc(Properties.LastName)*/
                .list();

        if (tagList.size()>0)
            return tagList.get(0);
        return null;
    }

    public List<LocationTagMap> fetchLocationTagMapsByLocation(Location location) {
        LocationTagMapDao locationTagMapDao = ELimsApplication.daoSession.getLocationTagMapDao();
        List<LocationTagMap> locationTagMapList = locationTagMapDao.queryBuilder()
                .where(LocationTagMapDao.Properties.LocationId.eq(location.getId()))
                .list();

        return locationTagMapList;
    }

    public List<LocationTag> fetchLocationTagsByLocation(Location location) {
        LocationTagDao locationTagDao = ELimsApplication.daoSession.getLocationTagDao();
        List<LocationTagMap> locationTagMapList = fetchLocationTagMapsByLocation(location);
        if(locationTagMapList == null) {
            return null;
        }
        List<LocationTag> tagList = new ArrayList<>();
        if(locationTagMapList.size()>0) {
            if (locationTagMapList.size() == 1) {
                tagList = locationTagDao.queryBuilder()
                        .where(LocationTagDao.Properties.Id.eq(locationTagMapList.get(0).getLocationTagId()))
                        .list();
            } else {
                WhereCondition[] whereConditions = new WhereCondition[locationTagMapList.size()];
                for (int i = 1; i < locationTagMapList.size(); i++) {
                    LocationTagMap locationTagMap = locationTagMapList.get(i);
                    whereConditions[i] = LocationTagDao.Properties.Id.eq(locationTagMap.getLocationTagId());
                }
                tagList = locationTagDao.queryBuilder()
                        .where(LocationTagDao.Properties.Id.eq(locationTagMapList.get(0).getLocationTagId())
                                , whereConditions)
                        .list();
            }
        }
        return tagList;
    }

    public LocationAttributeType fetchLocationAttributeTypeByName(String typeName) {
        LocationAttributeTypeDao locationAttributeTypeDao = ELimsApplication.daoSession.getLocationAttributeTypeDao();
        List<LocationAttributeType> tagList = locationAttributeTypeDao.queryBuilder()
                .where(LocationAttributeTypeDao.Properties.Name.eq(typeName))
            /*.orderAsc(Properties.LastName)*/
                .list();

        if (tagList.size()>0)
            return tagList.get(0);
        return null;
    }

    public LocationAttribute fetchLocationsAttributeByValue(String locationAttributeType, String value) {

        LocationAttributeType locationAttributeTypeObj = fetchLocationAttributeTypeByName(locationAttributeType);
        List<LocationAttribute> locationList = new ArrayList<>();
        // QueryBuilder.LOG_SQL = true;

        LocationAttributeDao locationDao = ELimsApplication.daoSession.getLocationAttributeDao();
        QueryBuilder<LocationAttribute> queryBuilder = locationDao.queryBuilder();
        queryBuilder.where(LocationAttributeDao.Properties.LocationAttributeTypeId.eq(locationAttributeTypeObj.getId()), LocationAttributeDao.Properties.Value.eq(value));

        locationList = queryBuilder.list();
        if (locationList.size()>0)
            return locationList.get(0);
        return null;
    }

    public LocationAttribute fetchLocationsAttribute(String locationAttributeType, String locationName) {
        Location location = fetchLocationByName(locationName);
        LocationAttributeType locationAttributeTypeObj = fetchLocationAttributeTypeByName(locationAttributeType);
        List<LocationAttribute> locationList = new ArrayList<>();
        // QueryBuilder.LOG_SQL = true;

        LocationAttributeDao locationDao = ELimsApplication.daoSession.getLocationAttributeDao();
        QueryBuilder<LocationAttribute> queryBuilder = locationDao.queryBuilder();
        queryBuilder.where(LocationAttributeDao.Properties.LocationAttributeTypeId.eq(locationAttributeTypeObj.getId()), LocationAttributeDao.Properties.LocationId.eq(location.getId()));

        locationList = queryBuilder.list();
        if (locationList.size()>0)
            return locationList.get(0);
        return null;
    }

    public int fetchNumberOfPatients() {
        PatientDao patientDao = ELimsApplication.daoSession.getPatientDao();
        QueryBuilder<Patient> queryBuilder = patientDao.queryBuilder();
        return queryBuilder.list().size();
    }

    public int fetchNumberOfTestOrders() {
        OrderDao orderDao = ELimsApplication.daoSession.getOrderDao();
        QueryBuilder<Order> queryBuilder = orderDao.queryBuilder();
        return queryBuilder.list().size();
    }

    public User fetchUserByUsername(String username) {
        UserDao userDao = ELimsApplication.daoSession.getUserDao();
        List<User> user = userDao.queryBuilder()
                .where(UserDao.Properties.UserName.like(username))
                .list();

        if(user.size()>0)
            return user.get(0);

        return null;
    }

    public int findNumberOfUsers() {
        UserDao userDao = ELimsApplication.daoSession.getUserDao();
        List<User> user = userDao.queryBuilder().list();

        return user.size();
    }

    public User fetchUserById(Long userId) {
        UserDao userDao = ELimsApplication.daoSession.getUserDao();
        List<User> user = userDao.queryBuilder()
                .where(UserDao.Properties.Id.eq(userId))
                .list();

        if(user.size()>0)
            return user.get(0);

        return null;
    }

    public Permission fetchPermissionByPermissionName(String permissionName) {
        PermissionDao userDao = ELimsApplication.daoSession.getPermissionDao();
        List<Permission> user = userDao.queryBuilder()
                .where(PermissionDao.Properties.PermissionName.eq(permissionName))
                .list();

        if(user.size()>0)
            return user.get(0);

        return null;
    }

    public UserPermissions fetchUserPermission(Long userId, String permission) {
        Permission perm = fetchPermissionByPermissionName(permission);
        if(perm == null)
            return null;

        UserPermissionsDao obsDao = ELimsApplication.daoSession.getUserPermissionsDao();
        List<UserPermissions> obsList = obsDao.queryBuilder()
                .where(UserPermissionsDao.Properties.UserId.eq(userId), UserPermissionsDao.Properties.PermissionId.eq(perm.getId()))
            /*.orderAsc(Properties.LastName)*/
                .list();

        if (obsList.size()>0)
            return obsList.get(0);
        return null;
    }

    public User fetchUserByUUID(String uuid) {
        UserDao userDao = ELimsApplication.daoSession.getUserDao();
        List<User> user = userDao.queryBuilder()
                .where(UserDao.Properties.Uuid.eq(uuid))
                .list();

        if(user.size()>0)
            return user.get(0);

        return null;
    }

    public SendableData fetchSenableDataByReferenceId(Long id) {
        SendableDataDao sendableDataDao = ELimsApplication.daoSession.getSendableDataDao();
        List<SendableData> sendableDataList = sendableDataDao.queryBuilder()
                .where(SendableDataDao.Properties.ReferenceId.eq(id))
                .list();

        if(sendableDataList.size()>0)
            return sendableDataList.get(0);

        return null;
    }

    public List<SendableData> fetchSenableDataWithError() {
        SendableDataDao sendableDataDao = ELimsApplication.daoSession.getSendableDataDao();
        List<SendableData> sendableDataList = sendableDataDao.queryBuilder()
                .where(SendableDataDao.Properties.NumberOfUploadAttempts.gt(0))
                .list();

        return sendableDataList;
    }

    public void reCreateDatabase() {
        DaoMaster.dropAllTables(ELimsApplication.daoSession.getDatabase(), true);
        DaoMaster.createAllTables(ELimsApplication.daoSession.getDatabase(), true);
    }

    public List<DrugOrders> fetchActiveDrugOrdersByPatientId(Long patientId) {
        long today = new LocalDate().toDate().getTime();
        int milisPassedToday = new LocalTime().getMillisOfDay();
        System.out.println("Today is: "+today);
        List<DrugOrders> drugOrders;
        DrugOrdersDao drugOrdersDao = ELimsApplication.daoSession.getDrugOrdersDao();
        drugOrders = drugOrdersDao.queryBuilder()
                .where(
                        DrugOrdersDao.Properties.PatientId.eq(patientId),
                        DrugOrdersDao.Properties.OrderAction.notEq(OpenMRSMappings.ORDER_ACTION_DISCONTINUE),
                        DrugOrdersDao.Properties.ScheduledDate.le(today))
                .whereOr(DrugOrdersDao.Properties.DateStopped.isNull(),
                        DrugOrdersDao.Properties.DateStopped.gt(today+milisPassedToday))
                .list();

        return drugOrders;

    }

    public Drug fetchDrugByConceptId(Long conceptId) {
        if(conceptId==null)
            return null;
        DrugDao drugDao = ELimsApplication.daoSession.getDrugDao();
        List<Drug> drugList = drugDao.queryBuilder()
                .where(DrugDao.Properties.ConceptId.eq(conceptId))
                .list();
        if (drugList.size()>0)
            return drugList.get(0);
        else
            return null;
    }

    public Drug fetchDrugByName(String name) {
        if(name==null)
            return null;
        DrugDao drugDao = ELimsApplication.daoSession.getDrugDao();
        List<Drug> drugList = drugDao.queryBuilder()
                .where(DrugDao.Properties.Name.like(name+"%"))
                .list();
        if (drugList.size()>0)
            return drugList.get(0);
        else
            return null;
    }
    public Drug fetchDrugByUUID(String uuid) {
        if(uuid==null)
            return null;
        DrugDao drugDao = ELimsApplication.daoSession.getDrugDao();
        List<Drug> drugList = drugDao.queryBuilder()
                .where(DrugDao.Properties.Uuid.eq(uuid))
                .list();
        if (drugList.size()>0)
            return drugList.get(0);
        else
            return null;
    }

}
