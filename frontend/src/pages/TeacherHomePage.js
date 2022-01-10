import Nav from '../components/AppNavBar';
import { Button, Form, TeacherButton } from '../components/HomePageCSS';
import { GetStudentsTable, GetAbsencesTable, GetFeedbacksTable } from '../components/Table';
import { useEffect, useState } from 'react';
import axios from 'axios';

export function TeacherHomePage() {
    const [Grades, setGrades] = useState(false);
    const [Absences, setAbsences] = useState(false);
    const [Feedbacks, setFeedback] = useState(false);
    const userJSON = JSON.parse(localStorage.getItem('user'));

    const [GradesData, setGradesData] = useState('');
    const [AbsencesData, setAbsencesData] = useState('');
    const [FeedbacksData, setFeedbacksData] = useState('');
    const [SubjectsData, setSubjectsData] = useState(null);

    useEffect(() => {

    })

    function onGrades() {
        console.log(userJSON)
        axios.get('http://localhost:8080/student/byClass?stClass=' + userJSON.subject.sjClass + 'A')
            .then(function (response) {
                if (typeof response.data === 'undefined')
                    return;
                setGradesData(response.data)
                setGrades(true);
                setAbsences(false);
                setFeedback(false);
            })
            .catch(function (error) {
                alert(error)
                return;
            })
    }

    function onAbsences() {
        axios.get('http://localhost:8080/student/absences?stId=' + userJSON.uuid)
            .then(function (response) {
                if (typeof response.data === 'undefined')
                    return;
                setAbsencesData(response.data);
                setGrades(false);
                setAbsences(true);
                setFeedback(false);
            })
            .catch(function (error) {
                alert(error)
                return;
            })
    }

    function onFeedback() {
        axios.get('http://localhost:8080/student/feedbacks?stId=' + userJSON.uuid)
            .then(function (response) {
                setFeedbacksData(response.data);
                setGrades(false);
                setAbsences(false);
                setFeedback(true);
            })
            .catch(function (error) {
                alert(error)
                return;
            })
    }

    return (
        <Form>
            <Nav />
            <Button onClick={onGrades} width='24.2%' selected={Grades}>Оценки</Button>
            <Button onClick={onAbsences} width='24.2%' selected={Absences}>Отсъствия</Button>
            <Button onClick={onFeedback} width='24.2%' selected={Feedbacks}>Забележки</Button>
            <TeacherButton width='6%'>Оценка</TeacherButton>
            <TeacherButton width='8%'>Остъствие</TeacherButton>
            <TeacherButton width='8%'>Забележка</TeacherButton>
            {
                Grades && <GetStudentsTable rawData={GradesData} subjects={SubjectsData} />
            }
            {
                Absences && <GetAbsencesTable rawData={AbsencesData} />
            }
            {
                Feedbacks && <GetFeedbacksTable rawData={FeedbacksData} />
            }
        </Form>
    )
}