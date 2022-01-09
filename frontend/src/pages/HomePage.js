import Nav from '../components/AppNavBar';
import { Button, Form } from '../components/HomePageCSS';
import { GetMarksTable, GetAbsencesTable, GetFeedbacksTable } from '../components/Table';
import { useState } from 'react';

function HomePage() {

    const [Grades, setGrades] = useState(true);
    const [Absences, setAbsences] = useState(false);
    const [Feedbacks, setFeedback] = useState(false);

    function onGrades() {
        setGrades(true);
        setAbsences(false);
        setFeedback(false);
    }

    function onAbsences() {
        setGrades(false);
        setAbsences(true);
        setFeedback(false);
    }

    function onFeedback() {
        setGrades(false);
        setAbsences(false);
        setFeedback(true);
    }

    return (
        <Form>
            <Nav />
            <Button onClick={onGrades}>Оценки</Button>
            <Button onClick={onAbsences}>Отсъствия</Button>
            <Button onClick={onFeedback}>Забележки</Button>
            {
                Grades && <GetMarksTable></GetMarksTable>
            }
            {
                Absences && <GetAbsencesTable></GetAbsencesTable>
            }
            {
                Feedbacks && <GetFeedbacksTable></GetFeedbacksTable>
            }

        </Form>
    )
}

export default HomePage;