package com.example.cms.model.repository;

import com.example.cms.model.entity.CourseMark;
import com.example.cms.model.entity.CourseMarkKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseMarkRepository extends JpaRepository<CourseMark, CourseMarkKey> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE marks " +
                    "SET mark = mark + 5 " +
                    "WHERE courseCode = :code " +
                    "AND mark <= 95",
            nativeQuery = true)
    void increaseFive(@Param("code") String code);

    // this add condition to add grade >= 95 100 without exceeding 100, but idk if
    // meets the requirements
//    UPDATE marks
//SET mark = CASE
//              WHEN mark + 5 <= 100 THEN mark + 5
//              ELSE 100
//           END
//WHERE courseCode = :code;
}
