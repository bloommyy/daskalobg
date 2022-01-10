import Nav from '../components/AppNavBar';
import { Button, Form } from '../components/HomePageCSS';
import { GetMarksTable, GetAbsencesTable, GetFeedbacksTable } from '../components/Table';
import { useEffect, useState } from 'react';
import axios from 'axios';

function HomePage() {

    const [Grades, setGrades] = useState(false);
    const [Absences, setAbsences] = useState(false);
    const [Feedbacks, setFeedback] = useState(false);
    const userJSON = JSON.parse(localStorage.getItem('user'));

    const [GradesData, setGradesData] = useState('');
    const [AbsencesData, setAbsencesData] = useState('');
    const [FeedbacksData, setFeedbacksData] = useState('');
    const [SubjectsData, setSubjectsData] = useState(null);

    useEffect(() => {
        if (SubjectsData === null)
            axios.get('http://localhost:8080/subject/subjectsByClass?sjClass=' + userJSON.stClass.slice(0, -1))
                .then(function (response) {
                    console.log(response)
                    if (typeof response.data === 'undefined')
                        return;
                    setSubjectsData(response.data);
                })
                .catch(function (error) {
                    alert(error)
                    return;
                })
    })

    function onGrades() {
        axios.get('http://localhost:8080/student/marks?stId=' + userJSON.uuid)
            .then(function (response) {
                console.log(response)
                if (typeof response.data === 'undefined')
                    return;
                setGradesData(response.data)
            })
            .catch(function (error) {
                alert(error)
                return;
            })

        setGrades(true);
        setAbsences(false);
        setFeedback(false);
    }

    function onAbsences() {
        axios.get('http://localhost:8080/student/absences?stId=' + userJSON.uuid)
            .then(function (response) {
                if (typeof response.data === 'undefined')
                    return;
                setAbsencesData(response.data);
            })
            .catch(function (error) {
                alert(error)
                return;
            })

        setGrades(false);
        setAbsences(true);
        setFeedback(false);
    }

    function onFeedback() {
        axios.get('http://localhost:8080/student/feedbacks?stId=' + userJSON.uuid)
            .then(function (response) {
                setFeedbacksData(response.data);
            })
            .catch(function (error) {
                alert(error)
                return;
            })

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
                Grades && <GetMarksTable rawData={GradesData} subjects={SubjectsData} />
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

export default HomePage;