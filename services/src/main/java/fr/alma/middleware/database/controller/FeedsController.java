package fr.alma.middleware.database.controller;


/**
* This class provides methods to link PMVs ids from API getTempsParcours and csv file.
* @author mael
*/
public class FeedsController {

    
	/*@Inject
    private ItineraryController itineraryContr;*/
    /*@Inject
    private LinkageController linkageContr;*/

    /**
     * Import all the PMV from the open data of nantes on the database
     * @throws FileNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public void importPMV() throws FileNotFoundException, IOException, SQLException {

            List<String[]> data = ParserCSV.extractData(System.getProperty("user.dir")+"/localisation_pmv.csv");
            
            ItineraryController itineraryContr = new ItineraryController();
            LinkageController linkageContr = new LinkageController();

            for (String[] oneData : data) {
                String id = oneData[0].replace(',', '.');
                String sens = oneData[1];
                String indic_temps = oneData[2];
                String longitude = oneData[3].replace(',', '.');
                String latitude = oneData[4].replace(',', '.');

                PMV pmv = new PMV((int)Float.parseFloat(id),
                                    sens,
                                    ("Oui".equals(indic_temps)) ? true : false,
                                    Float.parseFloat(longitude),
                                    Float.parseFloat(latitude),
                                    itineraryContr.getItinerariesByNumero(linkageContr.getPmvToItineraryId((int)Float.parseFloat(id))));

                this.add(pmv);
            }
    }

    /**
* Add the PMV to the database.
* @param pmv
*/
    public void add(PMV pmv) throws SQLException{

            Statement s = DataBaseManager.getInstance().getCon().createStatement();
                        int indic = (pmv.isIndic_temps())? 1 : 0;
            String sqlquery = "INSERT INTO Pmv (numero,sens,indic_temps_parcours,longitude,latitude)"
                                + "VALUES('"+ pmv.getId() + "', "
                                + "'" + pmv.getSens() + "', "
                                + "'" + indic + "', "
                                + "'" + pmv.getLongitude() + "', "
                                + "'" + pmv.getLatitude() + "') ";
            System.out.println(sqlquery);
            s.executeUpdate(sqlquery);
    }

    /**
* Return all the PMVs from Pmv table.
* @return a List of PMVs
* @throws SQLException
*/
    public List<PMV> getAll() throws SQLException{
        List<PMV> pmvs = new ArrayList();
        
        ItineraryController itineraryContr = new ItineraryController();
        LinkageController linkageContr = new LinkageController();


        Statement s = DataBaseManager.getInstance().getCon().createStatement();
        String sqlquery = "SELECT * FROM Pmv;";
        ResultSet res = s.executeQuery(sqlquery);

        while(res.next()){
                pmvs.add(new PMV(res.getInt("numero"),res.getString("sens"),
                                    res.getBoolean("indic_temps_parcours"),
                                    res.getFloat("longitude"),
                                    res.getFloat("latitude"),
                                    itineraryContr.getItinerariesByNumero(linkageContr.getPmvToItineraryId(res.getInt("numero")))));
            }

        return pmvs;
    }

    /**
* Remove all the PMVs in Pmv table.
* @throws SQLException
*/
    public void removeAll() throws SQLException{
        Statement s = DataBaseManager.getInstance().getCon().createStatement();
        String sqlquery = "DELETE FROM Pmv;";
        s.executeUpdate(sqlquery);
    }
    
    /**
* Test this class
* @param args
*/
    public static void main(String args[]){

        System.out.print(System.getProperty("user.dir" ));
        PMVController pmvContr = new PMVController();
        try {
            pmvContr.removeAll();
        } catch (SQLException ex) {
            Logger.getLogger(PMVController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try {
                pmvContr.importPMV();
            } catch (SQLException ex) {
                Logger.getLogger(PMVController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(PMVController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            System.out.println(pmvContr.getAll().size());
        } catch (SQLException ex) {
            Logger.getLogger(PMVController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
