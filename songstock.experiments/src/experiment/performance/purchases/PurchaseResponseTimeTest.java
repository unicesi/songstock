/**
 * 
 */
package experiment.performance.purchases;

import junit.framework.Test;

import com.clarkware.junitperf.LoadTest;
import com.clarkware.junitperf.TestFactory;
import com.clarkware.junitperf.TimedTest;


/**
 * @author Andrés Paz
 *
 */
public class PurchaseResponseTimeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suiteTimedTest());
		junit.textui.TestRunner.run(suiteLoadAndTimedTest());
	}
	
	private static Test suiteTimedTest() {
		long maxTimeInMillis = 5000;

        Test test = new TestFactory(PurchaseTest.class);
        Test timedTest = new TimedTest(test, maxTimeInMillis);
        return timedTest;
	}

	private static Test suiteLoadAndTimedTest() {
		long maxTimeInMillis = 5000;
		int concurrentUsers = 125;

        Test test = new TestFactory(PurchaseTest.class);
        Test timedTest = new TimedTest(test, maxTimeInMillis);
        Test loadTest = new LoadTest(timedTest, concurrentUsers);
        return loadTest;
	}

}
