/* ${1} */
UPDATE nc_objects SET version = version + 1 WHERE object_id = ${0};
DELETE FROM nc_params WHERE object_id = ${0} AND attr_id IN (9137434520013672033 /* Lattitude, dd */, 9137434520013672035 /* Longitude, dd */);
INSERT INTO nc_params (object_id, attr_id, value) VALUES (${0}, 9137434520013672035, '${4}');
INSERT INTO nc_params (object_id, attr_id, value) VALUES (${0}, 9137434520013672033, '${5}');
