import ptolemy.plot.*;

public class EasyPtPlot {
	public static final double Xmin = 1995;
	public static final double Xmax = 2022;


	public static void main(String[] args) {
		Plot plotObj = new Plot(); // Create Plot object
		plotObj.setTitle("MONTHS BASED OFF OF MAXIMUM RAINFALL OVER A TWO DAY PERIOD");
		plotObj.setXLabel("year");
		plotObj.setYLabel("Max rainfall over 2 days (mm)");

		for (double x = Xmin; x <= Xmax; x++) {
			for (double month = 0; month < 12; month++) {
				double y = Main.perMonth(Main.fileInput((int) x))[(int) month];
				if (x != 2022 && !(month >= 4))
					plotObj.addPoint(0, x + (month / 12), y, true);
			}
		}

		Plot plotObj2 = new Plot(); // Create Plot object
		plotObj2.setTitle("");
		plotObj2.setXLabel("x");
		plotObj2.setYLabel("f(x)");
		for (double x = Xmin; x <= Xmax; x++) {
			for (double month = 0; month < 12; month++) {
				double y = Main.topPerMonth(Main.fileInput((int) x), (int) month);
				if (x != 2022 && month <= 4) {
					plotObj2.addPoint(0, x + (month / 12), y, true);
				}
			}
		}

		PlotApplication app = new PlotApplication(plotObj2); // Display
		PlotApplication app2 = new PlotApplication(plotObj);
	}

}