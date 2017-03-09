
public class City {
	private int cityNum;
	private String cityCode;
	private String cityName;
	private int population;
	private int elevation;
	
	public City(int n, String code, String name, int pop, int elev) {
		cityNum = n;
		cityCode = code;
		cityName = name;
		population = pop;
		elevation = elev;
	}
	public int getNum() { return cityNum; }
	public String getCode() { return cityCode; }
	public String getName() { return cityName; }
	public int getPop() { return population; }
	public int getElev() { return elevation; }
}
