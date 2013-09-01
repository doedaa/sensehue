package nl.sensibleHue

import nl.sense_os.api.Api;
import nl.sense_os.objects.Params;

class sensehue {

	static main(args) {
		String credentials = "credentials.txt";
		Api.login(credentials);

		// verbosity
		Params.General.verbosity = true;
	}

}
