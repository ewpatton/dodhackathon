#!/bin/bash

CSV2RDF4LOD_HOME=${CSV2RDF4LOD_HOME:?"not set; source csv2rdf4lod/source-me.sh or see https://github.com/timrdf/csv2rdf4lod-automation/wiki/CSV2RDF4LOD-not-set"}
#sourceID="military-nutrition"
#datasetID="mre"
#versionID="2013"
eID="1"

#graph="https://github.com/timrdf/csv2rdf4lod-automation/wiki/CSV2RDF4LOD_BASE_URI#/source/military-nutrition/dataset/mre/version/2013"

export CSV2RDF4LOD_FORCE_PUBLISH="true"
source $CSV2RDF4LOD_HOME/bin/convert-aggregate.sh
export CSV2RDF4LOD_FORCE_PUBLISH="false"
