# Copyright DataStax, Inc.
#
# Please see the included license file for details.
#
from six.moves import configparser
import sys

from cassandra.auth import DSEPlainTextAuthProvider, DSEGSSAPIAuthProvider


def get_auth_provider(config_file, env, username=None, password=None, options=None):
    def print_debug(message):
        if options and hasattr(options, "debug") and options.debug:
            sys.stderr.write(message)

    if username:
        print_debug("Using DSEPlainTextAuthProvider\n")
        return DSEPlainTextAuthProvider(username=username, password=password)

    """
    Kerberos auth provider can be configured in the cqlshrc file [kerberos] section
    or with environment variables:

    Config option      Environment Variable      Description
    -------------      --------------------      -----------
    service            KRB_SERVICE               Service to authenticate with
    qops               QOPS                      Comma separated list of QOP values (default: auth)
    """
    configs = configparser.SafeConfigParser() if sys.version_info < (3, 2) else configparser.ConfigParser()
    configs.read(config_file)

    def get_option(section, option, env_variable, default=None):
        value = env.get(env_variable)
        if value is None:
            try:
                value = configs.get(section, option)
            except configparser.Error:
                value = default
        return value

    krb_service = get_option('kerberos', 'service', 'KRB_SERVICE', 'dse')
    krb_qop_value = get_option('kerberos', 'qops', 'QOPS', 'auth')

    try:
        provider = DSEGSSAPIAuthProvider(service=krb_service, qops=krb_qop_value.split(','))
        print_debug("Using DSEGSSAPIAuthProvider(service=%s, qops=%s)\n" % (krb_service, krb_qop_value))
        print_debug("    This will only be used if the server requests kerberos authentication\n")
        return provider
    except ImportError as e:
        print_debug("Attempted to use DSEGSSAPIAuthProvider(service=%s, qops=%s)\n" % (krb_service, krb_qop_value))
        print_debug("    Attempt failed because: %s\n" % str(e))
        return None
