require 'pathname'
require 'cocoapods'

# Setup the CooeeSDK pod and dependent pod to work with Flipper pod
# Params:
#  - installer: Pod::Installer
def setup_cooee(installer)
  Pod::Installer::Xcode::TargetValidator.send(:define_method, :verify_no_static_framework_transitive_dependencies) {}
  installer.pod_targets.each do |pod|
    iscooeedependency = pod.name == 'CooeeSDK' || pod.name == 'HandyJSON' || pod.name == 'Sentry'
    unless iscooeedependency
      def pod.build_type
        Pod::BuildType.static_library
      end
    end
  end
end
