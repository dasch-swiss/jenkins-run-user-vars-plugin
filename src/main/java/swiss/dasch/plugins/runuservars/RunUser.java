package swiss.dasch.plugins.runuservars;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.jenkinsci.plugins.builduser.varsetter.IUsernameSettable;
import org.jenkinsci.plugins.builduser.varsetter.impl.UserIdCauseDeterminant;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.User;
import hudson.model.Cause.UserIdCause;
import hudson.tasks.BuildWrapperDescriptor;
import jenkins.tasks.SimpleBuildWrapper;

public class RunUser extends SimpleBuildWrapper {

	@DataBoundConstructor
	public RunUser() {
	}

	@Override
	public void setUp(Context context, Run<?, ?> build, FilePath workspace, Launcher launcher, TaskListener listener,
			EnvVars initialEnvironment) {

		Map<String, String> vars = createCurrentRunUserVariables();

		for (Map.Entry<String, String> entry : vars.entrySet()) {
			context.env(entry.getKey(), entry.getValue());
		}

	}

	public static Map<String, String> createCurrentRunUserVariables() {
		User currentUser = User.current();

		String userId = currentUser != null ? currentUser.getId() : null;

		return createRunUserVariables(userId);
	}

	public static Map<String, String> createRunUserVariables(@Nullable String userId) {
		Map<String, String> buildVars = new HashMap<>();

		// Let Build User Vars plugin extract the user information
		IUsernameSettable<UserIdCause> setter = new UserIdCauseDeterminant();
		setter.setJenkinsUserBuildVars(new UserIdCause(userId), buildVars);

		Map<String, String> runVars = new HashMap<>();

		for (Entry<String, String> entry : buildVars.entrySet()) {
			String var = entry.getKey();
			String value = entry.getValue();

			if (var.startsWith("BUILD_")) {
				runVars.put(var.replaceFirst("BUILD_", "RUN_"), value);
			}
		}

		return runVars;
	}

	@Extension
	public static class DescriptorImpl extends BuildWrapperDescriptor {
		@Override
		public boolean isApplicable(AbstractProject<?, ?> item) {
			return true;
		}

		@Override
		public String getDisplayName() {
			return Messages.RunUserVars_RunUserBuildStep_DisplayName();
		}
	}

}
