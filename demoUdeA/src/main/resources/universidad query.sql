select DISTINCT e.id, e.nombres, e.apellidos, e.carrera from estudiante e 
inner join estudiante_materia em on em.estudiante_id = e.id
inner join materia m on m.id = em.materia_id
where m.nombre = "BASE DE DATOS";

select DISTINCT p.id, p.nombres, p.apellidos, p.carrera 
from profesor p 
inner join profesor_materia pm on pm.profesor_id = p.id 
inner join materia m on m.id = pm.materia_id 
where m.nombre = "ARQ DE SOFTWARE";