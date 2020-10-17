package org.firstinspires.ftc.teamcode.Autonomous_Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.MecanumHardware;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

/* New line adding for testing GIT change*/

@Autonomous(name="SkyStone Color Testing", group="Pushbot")
public class Testing extends LinearOpMode {

    /* Declare OpMode members. */
    MecanumHardware robot = new MecanumHardware();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();


    double Drive;
    double Turn;
    double HStrafe;
    double VStrafe;


    @Override
    public void runOpMode() {



        /* Arm code for HeightMotor, which raises the arm up and down*/
        robot.HeightMotor = hardwareMap.dcMotor.get("HeightMotor");
        robot.HeightMotor.setZeroPowerBehavior(BRAKE);
        robot.HeightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        robot.HeightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.HeightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int HeightUpPos = 300;
        int HeightDownPos = -115;
        int Htarget = 0;
        int HeightDown2 = 0;
        //--------------------------------------------------------------------------------------

        /* Arm code for ElbowMotor that acts like an elbow would */
        robot.ElbowMotor = hardwareMap.dcMotor.get("ElbowMotor");
        robot.ElbowMotor.setZeroPowerBehavior(BRAKE);
        robot.ElbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //while (HeightMotor.getCurrentPosition() != 0) {
        //idle();
        //}
        robot.ElbowMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.ElbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int ElbowUpPos = 450;
        int ElbowDownPos = 510;
        int Etarget = 0;
        //--------------------------------------------------------------------------------------
        Drive = 0.5;
        HStrafe = 0.3;



        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */

        robot.init(hardwareMap);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Close claw and close foundation grabber
        robot.Servo2.setPosition(0);
        robot.Servo0.setPosition(0);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        robot.Servo0.setPosition(1);

        // Unfold the arm
        robot.HeightMotor.setTargetPosition(HeightUpPos);
        robot.ElbowMotor.setTargetPosition(ElbowDownPos);
        if (robot.HeightMotor.isBusy()) {
            robot.HeightMotor.setPower(0.4);

        } else {
            robot.HeightMotor.setPower(0);
        }

        if (robot.ElbowMotor.isBusy()) {
            robot.ElbowMotor.setPower(0.4);

        } else {
            robot.ElbowMotor.setPower(0);
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Unfold", "%2.5f S Elapsed", runtime.seconds());
            telemetry.addData("encoder-height", robot.HeightMotor.getCurrentPosition());
            telemetry.addData("target", HeightUpPos);
            telemetry.addData("encoder-elbow", robot.ElbowMotor.getCurrentPosition());
            telemetry.addData("target", ElbowDownPos);
            telemetry.update();
        }

        // Unfold Arm by putting elbow down
        robot.HeightMotor.setTargetPosition(HeightDownPos);
        if (robot.HeightMotor.isBusy()) {
            robot.HeightMotor.setPower(0.4);

        } else {
            robot.HeightMotor.setPower(0);
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {
            telemetry.addData("Unfold2", "%2.5f S Elapsed", runtime.seconds());
            telemetry.addData("encoder-height", robot.HeightMotor.getCurrentPosition());
            telemetry.addData("target", HeightDownPos);
            telemetry.addData("encoder-elbow", robot.ElbowMotor.getCurrentPosition());
            telemetry.addData("target", ElbowDownPos);
            telemetry.update();
        }


        // Drive forward towards stones
        robot.leftFront.setPower(Drive);
        robot.leftBack.setPower(Drive);
        robot.rightFront.setPower(Drive);
        robot.rightBack.setPower(Drive);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 0.77)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);

        sleep(2000);


        while (opModeIsActive() && (runtime.seconds() < 10)) {

            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.addData("C.S. RED", robot.FrontColorSensor.red()); // Red channel value
            telemetry.update();


            while (robot.FrontColorSensor.red() > 28) {


                // Strafe Right down the line of blocks
                robot.leftFront.setPower(HStrafe);
                robot.leftBack.setPower(-HStrafe);
                robot.rightFront.setPower(-HStrafe);
                robot.rightBack.setPower(HStrafe);
                runtime.reset();
                while (opModeIsActive() && (runtime.seconds() < 3)) {
                    telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
                    telemetry.addData("C.S. RED", robot.FrontColorSensor.red()); // Red channel value
                    telemetry.update();
                }

                if (robot.FrontColorSensor.red() < 25) {

                    // Strafes left for 0.2 seconds towards the wall
                    robot.leftFront.setPower(-HStrafe);
                    robot.leftBack.setPower(HStrafe);
                    robot.rightFront.setPower(HStrafe);
                    robot.rightBack.setPower(-HStrafe);
                    runtime.reset();
                    while (opModeIsActive() && (runtime.seconds() < 0.05)) {
                        telemetry.addData("Path", "Leg 1: Strafes left for 0.2 sec %2.5f S Elapsed", runtime.seconds());
                        telemetry.update();
                    }

                    // Drive forward towards stones
                    robot.leftFront.setPower(Drive);
                    robot.leftBack.setPower(Drive);
                    robot.rightFront.setPower(Drive);
                    robot.rightBack.setPower(Drive);
                    runtime.reset();

                    while (opModeIsActive() && (runtime.seconds() < 0.1)) {
                        telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
                        telemetry.update();
                    }

                    //Stop
                    robot.leftFront.setPower(0);
                    robot.leftBack.setPower(0);
                    robot.rightFront.setPower(0);
                    robot.rightBack.setPower(0);
                    runtime.reset();
                    while (opModeIsActive() && (runtime.seconds() < 0.3)) {
                        telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
                        telemetry.addData("C.S. RED", robot.FrontColorSensor.red()); // Red channel value
                        telemetry.update();
                    }

                    sleep(500);

                    Drive = 0.7;
                    robot.Servo0.setPosition(0);

                    sleep(500);


                    /* Lift the skystone
                    robot.HeightMotor.setTargetPosition(HeightDown2);
                    if (robot.HeightMotor.isBusy()) {
                        robot.HeightMotor.setPower(0.4);

                    } else {
                        robot.HeightMotor.setPower(0);
                    }
                    runtime.reset();
                    while (opModeIsActive() && (runtime.seconds() < 3.0)) {
                        telemetry.addData("Unfold2", "%2.5f S Elapsed", runtime.seconds());
                        telemetry.addData("encoder-height", robot.HeightMotor.getCurrentPosition());
                        telemetry.addData("target", HeightDownPos);
                        telemetry.addData("encoder-elbow", robot.ElbowMotor.getCurrentPosition());
                        telemetry.addData("target", ElbowDownPos);
                        telemetry.update();
                    }
                    */


                    // Move backwards
                    robot.leftFront.setPower(-Drive);
                    robot.leftBack.setPower(-Drive);
                    robot.rightFront.setPower(-Drive);
                    robot.rightBack.setPower(-Drive);
                    runtime.reset();
                    while (opModeIsActive() && (runtime.seconds() < 0.8)) {
                        telemetry.addData("Path", "Leg 2: Drives backward for 0.5 sec %2.5f S Elapsed", runtime.seconds());
                        telemetry.update();
                    }

                    // Step 11: Turns for 0.85 seconds to bring the arm down before parking
                    robot.leftFront.setPower(-Drive);
                    robot.leftBack.setPower(-Drive);
                    robot.rightFront.setPower(Drive);
                    robot.rightBack.setPower(Drive);
                    runtime.reset();
                    while (opModeIsActive() && (runtime.seconds() < 0.8)) {
                        telemetry.addData("Path", "Leg 11: Turns for 0.85 sec %2.5f S Elapsed", runtime.seconds());
                        telemetry.update();
                    }

                    // Strafes left for 0.2 seconds towards the wall
                    robot.leftFront.setPower(-HStrafe);
                    robot.leftBack.setPower(HStrafe);
                    robot.rightFront.setPower(HStrafe);
                    robot.rightBack.setPower(-HStrafe);
                    runtime.reset();
                    while (opModeIsActive() && (runtime.seconds() < 1)) {
                        telemetry.addData("Path", "Leg 1: Strafes left for 0.2 sec %2.5f S Elapsed", runtime.seconds());
                        telemetry.update();
                    }

                    // Move Forewards
                    robot.leftFront.setPower(Drive);
                    robot.leftBack.setPower(Drive);
                    robot.rightFront.setPower(Drive);
                    robot.rightBack.setPower(Drive);
                    runtime.reset();
                    while (opModeIsActive() && (runtime.seconds() < 1)) {
                        telemetry.addData("Path", "Leg 2: Drives backward for 0.5 sec %2.5f S Elapsed", runtime.seconds());
                        telemetry.update();
                    }


                    sleep(500);
                    robot.Servo0.setPosition(1);
                    sleep(500);

                    // Move Forewards
                    robot.leftFront.setPower(-Drive);
                    robot.leftBack.setPower(-Drive);
                    robot.rightFront.setPower(-Drive);
                    robot.rightBack.setPower(-Drive);
                    runtime.reset();
                    while (opModeIsActive() && (runtime.seconds() < 0.5)) {
                        telemetry.addData("Path", "Leg 2: Drives backward for 0.5 sec %2.5f S Elapsed", runtime.seconds());
                        telemetry.update();
                    }

                    // Strafes left for 0.2 seconds towards the wall
                    robot.leftFront.setPower(-HStrafe);
                    robot.leftBack.setPower(HStrafe);
                    robot.rightFront.setPower(HStrafe);
                    robot.rightBack.setPower(-HStrafe);
                    runtime.reset();
                    while (opModeIsActive() && (runtime.seconds() < 0.5)) {
                        telemetry.addData("Path", "Leg 1: Strafes left for 0.2 sec %2.5f S Elapsed", runtime.seconds());
                        telemetry.update();
                    }

                    stop();


                }
            }
        }
    }
}

