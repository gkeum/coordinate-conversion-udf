/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//import org.gavaghan.geodesy.GeodeticCalculator;
//import io.trino.spi.function.StandardFunctionResolution;
//import io.trino.spi.connector.ConnectorFactory;

package io.trino.conversionfunctions;

// Importing necessary classes from the Trino library
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import io.trino.spi.function.ScalarFunction;
import io.trino.spi.function.SqlNullable;
import io.trino.spi.function.SqlType;
import io.trino.spi.type.StandardTypes;
import mil.nga.grid.features.Point;
import mil.nga.mgrs.MGRS;

public final class MGRSUDFPlugin
{
    private MGRSUDFPlugin()
    {}
    /**
     * A Scalar Function that converts an MGRS (Military Grid Reference System) coordinate to Latitude and Longitude.
     * The function takes a string representing an MGRS coordinate and returns a string representing the latitude and longitude.
     * param mgrs - The MGRS coordinate in string format
     * return A string in the format "latitude,longitude"
     */

    @ScalarFunction("mgrs_to_latlong")// Annotation indicating that this is a scalar function named "mgrs_to_latlong"
    @SqlType(StandardTypes.VARCHAR)// Annotation indicating that the return type of this function is VARCHAR (a string)
    public static Slice mgrsToLatLong(@SqlType(StandardTypes.VARCHAR) Slice mgrs)
            throws Exception
    {
        // Convert the MGRS coordinate to a latitude and longitude
        String mgrsstr = mgrs.toStringUtf8();
        MGRS mgrs1 = MGRS.parse(mgrsstr);
        Point point = mgrs1.toPoint();
        Double xCoord = point.getX();
        Double yCoord = point.getY();
        String latLongstr = yCoord.toString() + ", " + xCoord.toString();
        Slice latLongslice = Slices.utf8Slice(latLongstr);
        // Return the latitude and longitude as a Slice
        return latLongslice;
    }
    /**
     * A Scalar Function that converts Latitude and Longitude to an MGRS (Military Grid Reference System) coordinate.
     * The function takes a string representing latitude and longitude and returns a string representing the MGRS coordinate.
     * param latLong - The latitude and longitude in string format, separated by a comma
     * return The MGRS coordinate in string format
     */

    @ScalarFunction("latlong_to_mgrs")// Annotation indicating that this is a scalar function named "latlong_to_mgrs"
    @SqlType(StandardTypes.VARCHAR)// Annotation indicating that the return type of this function is VARCHAR (a string)
    @SqlNullable
    public static Slice latLongToMgrs(@SqlNullable @SqlType(StandardTypes.DOUBLE) Double latitude,
                                        @SqlNullable @SqlType(StandardTypes.DOUBLE) Double longitude)
    {
        // Check if any input is null
        if (latitude == null || longitude == null) {
            return null;  // Return null if any input is null
        }
        Point point2 = Point.point(longitude, latitude);
        MGRS mgrs2 = MGRS.from(point2);
        String mgrsStr = mgrs2.toString();
        Slice mgrsSlice = Slices.utf8Slice(mgrsStr);
        // Convert the latitude and longitude to an MGRS coordinate
        return mgrsSlice;
    }
}
