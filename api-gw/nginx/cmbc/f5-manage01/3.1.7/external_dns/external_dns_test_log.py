import logging

# Create a logger
logger = logging.getLogger()
logger.setLevel(logging.DEBUG)  # Set the desired log level (DEBUG, INFO, WARNING, ERROR, CRITICAL)

# Create a console handler and set its log level
console_handler = logging.StreamHandler()
console_handler.setLevel(logging.DEBUG)  # Set the desired log level for console output

# Create a formatter
formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')

# Assign the formatter to the handler
console_handler.setFormatter(formatter)

# Add the console handler to the logger
logger.addHandler(console_handler)

# Log messages
logger.debug('This is a debug message')
logger.info('This is an info message')
logger.warning('This is a warning message')
logger.error('This is an error message')
logger.critical('This is a critical message')

