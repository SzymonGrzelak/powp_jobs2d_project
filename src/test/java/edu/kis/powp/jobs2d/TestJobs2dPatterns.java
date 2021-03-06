package edu.kis.powp.jobs2d;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.adapter.DrawerAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.powp.jobs2d.events.SelectDriverCommandTestOptionListener;
import edu.kis.powp.jobs2d.events.SelectRectangleFactoryTestOptionListener;
import edu.kis.powp.jobs2d.events.SelectChangeVisibleOptionListener;
import edu.kis.powp.jobs2d.events.SelectCircleFactoryTestOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureJaneOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener2;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;

public class TestJobs2dPatterns {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 * 
	 * @param application Application context.
	 */
	private static void setupPresetTests(Application application) {
		SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager());

		application.addTest("Figure Joe 1", selectTestFigureOptionListener);

		SelectTestFigureOptionListener2 selectTestFigureOptionListener2 = new SelectTestFigureOptionListener2(
				DriverFeature.getDriverManager());

		application.addTest("Figure Joe 2", selectTestFigureOptionListener2);

		SelectTestFigureJaneOptionListener selectTestFigureJaneOptionListener = new SelectTestFigureJaneOptionListener(
				DriverFeature.getDriverManager());

		application.addTest("Figure Jane", selectTestFigureJaneOptionListener);

		SelectDriverCommandTestOptionListener selectCommandTestOptionListener = new SelectDriverCommandTestOptionListener(
				DriverFeature.getDriverManager());

		application.addTest("Figure Command", selectCommandTestOptionListener);

		SelectRectangleFactoryTestOptionListener selectRectangleFactoryOptionListener = new SelectRectangleFactoryTestOptionListener(
				DriverFeature.getDriverManager());

		application.addTest("Rectangle Factory", selectRectangleFactoryOptionListener);
		
		SelectCircleFactoryTestOptionListener selectCircleFactoryOptionListener = new SelectCircleFactoryTestOptionListener(
				DriverFeature.getDriverManager());

		application.addTest("Circle Factory", selectCircleFactoryOptionListener);
	}

	/**
	 * Setup driver manager, and set default driver for application.
	 * 
	 * @param application Application context.
	 */
	private static void setupDrivers(Application application) {
		Job2dDriver loggerDriver = new LoggerDriver();
		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

		Job2dDriver testDriver = new DrawerAdapter();
		DriverFeature.addDriver("Buggy Simulator", testDriver);

		Job2dDriver testDriver2 = new LineDrawerAdapter(LineFactory.getDottedLine());
		DriverFeature.addDriver("DottedLine Simulator", testDriver2);

		Job2dDriver testDriver3 = new LineDrawerAdapter(LineFactory.getSpecialLine());
		DriverFeature.addDriver("SpecialLine Simulator", testDriver3);

		Job2dDriver testDriver4 = new LineDrawerAdapter(new MyILine(Color.BLUE, 40, false));
		DriverFeature.addDriver("MyLine Simulator", testDriver4);

		Job2dDriver testDriver5 = new FiguresJaneDriver(0, 0, new MyILine(Color.PINK, 10, false));
		DriverFeature.addDriver("Jane Simulator", testDriver5);

		DriverFeature.updateDriverInfo();
	}

	/**
	 * Auxiliary routines to enable using Buggy Simulator.
	 * 
	 * @param application Application context.
	 */
	private static void setupDefaultDrawerVisibilityManagement(Application application) {
		DefaultDrawerFrame defaultDrawerWindow = DefaultDrawerFrame.getDefaultDrawerFrame();
		application.addComponentMenuElementWithCheckBox(DrawPanelController.class, "Default Drawer Visibility",
				new SelectChangeVisibleOptionListener(defaultDrawerWindow), true);
		defaultDrawerWindow.setVisible(true);
	}

	/**
	 * Setup menu for adjusting logging settings.
	 * 
	 * @param application Application context.
	 */
	private static void setupLogger(Application application) {
		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
				(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application app = new Application("2d jobs Visio");
				DrawerFeature.setupDrawerPlugin(app);
				setupDefaultDrawerVisibilityManagement(app);

				DriverFeature.setupDriverPlugin(app);
				setupDrivers(app);
				setupPresetTests(app);
				setupLogger(app);

				app.setVisibility(true);
			}
		});
	}

}
