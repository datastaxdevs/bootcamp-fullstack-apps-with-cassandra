# Copyright DataStax, Inc.
#
# Please see the included license file for details.
#
import importlib
import os
import pkgutil
import re
import subprocess

from textwrap import TextWrapper

import cqlhelp

def get_terminal_help_topics():
    return [camel_case_splitter(name[3:]) for _, name, _ in pkgutil.iter_modules([os.path.dirname(cqlhelp.__file__)]) if name.startswith('cql')]

def camel_case_splitter(name):
    matches = re.finditer('.+?(?:(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])|$)', name)
    return "_".join([m.group(0).lower() for m in matches])

def print_terminal_help_topic(topic):
    helpmodule = importlib.import_module('cqlhelp.cql%s' % "".join(w.capitalize() for w in topic.split("_")))

    lines = helpmodule.__doc__.split('\n')

    try:
        rows, columns = subprocess.check_output(['stty', 'size']).split()
    except:
        rows = 25
        columns = 80

    wrapper = TextWrapper()
    wrapper.break_on_hyphens = False
    try:
        wrapper.width = int(columns)
    except ValueError:
        wrapper.width = 80

    for line in lines:
        if re.match(r'\s', line):
            line = line.strip()
            wrapper.initial_indent = '    '
            if line.startswith('-'):
                wrapper.subsequent_indent = '        '
            else:
                wrapper.subsequent_indent = '    '
        else:
            wrapper.initial_indent = ''
            wrapper.subsequent_indent = ''

        print(wrapper.fill(line))
