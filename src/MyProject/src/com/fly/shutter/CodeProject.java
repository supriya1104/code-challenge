package com.fly.shutter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CodeProject {

	// Maps to store the values of different types listed
	private static HashMap<String, Customer> cust_map = new HashMap<String, Customer>();
	private static HashMap<String, Site_Visit> site_map = new HashMap<String, Site_Visit>();
	private static HashMap<String, Image> image_map = new HashMap<String, Image>();
	private static HashMap<String, Order> order_map = new HashMap<String, Order>();

	public static void main(String[] args) throws IOException, ParseException {

		JSONParser parser = new JSONParser();
		JSONArray data = null;
		try {
			// JSONParser used to parse the data and store as JSONArray
			data = (JSONArray) parser.parse(new FileReader("input\\input.txt"));
			for (Object obj : data)

			{
				// Convert the JSONArray to Objects
				JSONObject events = (JSONObject) obj;
				// Reading the text file which is in JSON format
				ReadFileAndJsonData(events);
			}
			TopXSimpleLTVCustomers(10);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	// Reading the text file and convert into objects
	private static void ReadFileAndJsonData(JSONObject events) {
		String Type = (String) events.get("type");
		String Verb = (String) events.get("verb");

		// switch case to check the type/
		switch (Type) {
		// Customer is the type listed and it has customer_id, last_name,
		// adr_city, adr_state, event_time
		// check if the customer is new or old, if it is new set the parameters
		// and if it old,update the parameters
		case "CUSTOMER":

			String last_name = events.get("last_name").toString();
			String adr_city = events.get("adr_city").toString();
			String adr_state = events.get("adr_state").toString();
			String event_time = events.get("event_time").toString();
			if (Verb.equals("NEW") || Verb.equals("UPDATE")) {
				String key = (String) events.get("key");
				if (!cust_map.containsKey(key)) {
					Customer c_new = new Customer();
					c_new.setLast_name(last_name);
					c_new.setAdr_city(adr_city);
					c_new.setAdr_state(adr_state);
					c_new.setEvent_time(event_time);
					cust_map.put(key, c_new);
				} else {
					Customer c_update = cust_map.get(key);

					if (events.get("last_name") != null) {
						c_update.setLast_name(last_name);
					}
					if (events.get("adr_city") != null) {
						c_update.setAdr_city(adr_city);
					}
					if (events.get("adr_state") != null) {
						c_update.setAdr_state(adr_state);
					}
					if (events.get("event_time") != null) {
						c_update.setEvent_time(adr_state);
					}
					cust_map.put(key, c_update);
				}
			} else {
				System.out.println("Enter a valid verb- NEW/UPDATE");
			}
			break;

		// site_visit is the type listed and it has page_id, customer_id, tags
		// event_time
		// update the parameters accordingly and increment customer visited
		// value if the customer is present/already visited

		case "SITE_VISIT":
			if (Verb.equals("NEW")) {
				Site_Visit sv = new Site_Visit();
				String key = events.get("key").toString();
				String c_id = events.get("customer_id").toString();
				sv.setPage_id(key);
				sv.setEvent_time(events.get("event_time").toString());
				sv.setCustomer_id(c_id);
				sv.setTags(events.get("tags").toString());
				site_map.put(key, sv);
				Customer c = cust_map.get(c_id);
				// customer is already visited, increment the site visits.
				if (cust_map.containsKey(c_id)) {
					c.addsitevisit(sv);
				}
			} else {
				System.out.println("Enter Valid Verb --- NEW");
			}
			break;

		// Image is the type listed and it has image_id, customer_id,
		// camera_make, camera_model, event_time
		// update the parameters accordingly and increment customer added image
		// value if the customer is present/already uploaded

		case "IMAGE":
			if (Verb.equals("UPLOAD")) {
				Image img = new Image();
				String key = events.get("key").toString();
				String c_id = events.get("customer_id").toString();
				img.setKey(key);
				img.setEvent_time(events.get("event_time").toString());
				img.setCustomer_id(c_id);
				img.setCamera_make(events.get("camera_make").toString());
				img.setCamera_model(events.get("camera_model").toString());
				image_map.put(key, img);
				Customer c1 = cust_map.get(c_id);
				// if the customer is already uplaoded image
				if (cust_map.containsKey(c_id)) {
					c1.addImage(img);
				}
			} else {
				System.out.println("not valid verb");
			}
			break;

		// order is the type listed and it has order_id, customer_id,
		// total_amount, event_time
		// if the order is new or it has to be updated - update the parameters
		// accordingly
		// increment customer orders if the customer have previous orders

		case "ORDER":
			if (Verb.equals("NEW") || Verb.equals("UDPATE")) {
				Order o = new Order();
				String key = events.get("key").toString();
				String c_id = events.get("customer_id").toString();
				String event_d = events.get("event_time").toString();
				o.setKey(key);
				o.setEvent_time(event_d);
				o.setCustomer_id(c_id);
				double r = Double.parseDouble((String) events.get("total_amount"));
				o.setTotal_amount(r);
				order_map.put(key, o);
				Integer x1 = calWeek(event_d);
				Customer c1 = cust_map.get(c_id);
				if (cust_map.containsKey(c_id)) {
					if (c1.getWeek1() != null && c1.getWeek1() == x1) {
						c1.addweekOrder(o);
						c1.setWeek1(x1);
					} else {
						c1.addOrder(o);
						c1.setWeek1(x1);
					}
				}
				if (Verb.equals("UPDATE")) {

					c1.updateOrder(o);

				}
			} else {
				System.out.println("Not a valid verb - NEW/UPDATE");
			}
			break;

		default:
			System.out.println("NOT A VALID TYPE - CUSOMTER/SITE-VISIT/IMAGE/ORDER");
		}
	}

	// method to get the top 10 customers
	private static void TopXSimpleLTVCustomers(int i) {
		ArrayList<ProjOutput> ProjOutput = new ArrayList<>();
		for (Entry<String, Customer> it : cust_map.entrySet()) {

			Customer customer = it.getValue();
			ArrayList<Order> orders1 = customer.getAddweekorder();
			double tot_ord_amount = 0;

			int tot_order = orders1.size();

			for (Order order : orders1) {
				tot_ord_amount = tot_ord_amount + order.getTotal_amount();

			}
			int tot_visits = customer.getSitevisits().size();
			// method to calculate from shutterfly customer life span, total
			// visits, total orders,total amount of orders
			double topx = topx(10, tot_visits, tot_order, tot_ord_amount);

			ProjOutput.add(new ProjOutput(it.getKey(), topx));
		}

		TreeMap<Double, String> s = new TreeMap<Double, String>();

		for (ProjOutput l : ProjOutput) {
			s.put(l.getValue(), l.getc_id());
		}
		try {

			FileWriter fw = new FileWriter("output\\output.txt");
			fw.write("key" + "----" + "Value");
			fw.write(System.lineSeparator());

			for (Double sl : s.descendingMap().keySet()) {
				fw.write(s.get(sl) + ", " + sl);

				fw.write(System.lineSeparator());
			}

			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// method to calculate the LTV of a customer.
	private static double topx(int a, int tot_visits, int tot_order, double tot_ord_amount) {

		Double value = (double) (52 * a * (tot_ord_amount / tot_order) * tot_visits);

		return value;
	}

	// method to calulate the week of the event
	private static Integer calWeek(String event_time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = null;
		Integer y = null;
		try {
			date = format.parse(event_time);
			Calendar calender1 = new GregorianCalendar();
			calender1.setTime(date);
			y = calender1.get(Calendar.WEEK_OF_YEAR);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return y;
	}

}
