package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.Stat;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelEvent extends JPanel {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanelEvent(Stat stat, List<MeteoEvent> listMeteoEvent, double moy,
			double min, double max, String titre) {
		this.stat = stat;
		this.titre = titre;
		this.inctemp = 0;
		this.moy = moy;
		this.min = min;
		this.max = max;

		this.dataSeries = new XYSeries("test", true, true);
		seriesCollection = new XYSeriesCollection();

		geometry();
		control();
		apparence();

	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update() {
		System.out.println("Valeur stat:" + stat.getLast());
		inctemp++;
		dataSeries.add(inctemp, stat.getLast());
		moy = stat.getMoy();

		// target.setStartValue((moy - (moy * 0.1)));
		// target.setEndValue((moy + (moy * 0.1)));
		// target.setLabel("Moyenne: "+moy);

		xyplot.addRangeMarker(target, Layer.BACKGROUND);

	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry() {
		XYDataset xyDataset = addDataset(0.0, 0.0);
		JFreeChart jfreechart = createChart(xyDataset);

		ChartPanel chartpanel = new ChartPanel(jfreechart);

		BoxLayout Box = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(Box);
		add(chartpanel);
	}

	private void apparence() {
		// rien
	}

	private void control() {
		// rien
	}

	private XYDataset addDataset(double x, double y) {

		dataSeries.add(x, y);
		seriesCollection.addSeries(dataSeries);

		return seriesCollection;
	}

	private JFreeChart createChart(XYDataset xydataset) {
		JFreeChart jfreechart = ChartFactory.createXYLineChart(titre,
				"Temps [s]", "GNAAA", xydataset, PlotOrientation.VERTICAL,
				false, true, false);

		jfreechart.setBackgroundPaint(Color.black);

		xyplot = (XYPlot) jfreechart.getPlot();
		xyplot.setBackgroundPaint(Color.black);
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);

		target = new IntervalMarker((moy - (moy * 0.1)), (moy + (moy * 0.1)));

		target.setLabel("Moyenne");
		target.setLabelFont(new Font("Helvetica", Font.ITALIC, 12));
		target.setLabelAnchor(RectangleAnchor.LEFT);
		target.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
		target.setPaint(new Color(222, 222, 255, 64));
		xyplot.addRangeMarker(target, Layer.BACKGROUND);

		ValueAxis domainAxis = xyplot.getDomainAxis();
		domainAxis.setTickLabelPaint(Color.white);
		domainAxis.setTickLabelFont(new Font("Helvetica", Font.BOLD, 12));
		domainAxis.setLabelPaint(Color.white);

		NumberAxis rangeAxis = (NumberAxis) xyplot.getRangeAxis();
		rangeAxis.setTickLabelPaint(Color.WHITE);
		rangeAxis.setTickLabelFont(new Font("Helvetica", Font.BOLD, 12));
		rangeAxis.setLabelPaint(Color.white);

		return jfreechart;
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private Stat stat;
	private double moy;
	private double min;
	private double max;

	private String titre;
	private XYSeries dataSeries;
	private int inctemp;
	private XYSeriesCollection seriesCollection;
	private IntervalMarker target;
	private XYPlot xyplot;

}
