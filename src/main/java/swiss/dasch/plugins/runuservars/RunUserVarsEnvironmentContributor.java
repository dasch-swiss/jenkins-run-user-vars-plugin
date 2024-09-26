package swiss.dasch.plugins.runuservars;

import java.io.IOException;

import org.jenkinsci.plugins.builduser.BuildUserVarsConfig;

import hudson.EnvVars;
import hudson.Extension;
import hudson.model.EnvironmentContributor;
import hudson.model.Run;
import hudson.model.TaskListener;

@Extension
public class RunUserVarsEnvironmentContributor extends EnvironmentContributor {

	@SuppressWarnings("rawtypes")
	@Override
	public void buildEnvironmentFor(Run r, EnvVars envs, TaskListener listener)
			throws IOException, InterruptedException {

		if (BuildUserVarsConfig.get().isAllBuilds()) {
			envs.putAllNonNull(RunUser.createCurrentRunUserVariables());
		}

	}

}
